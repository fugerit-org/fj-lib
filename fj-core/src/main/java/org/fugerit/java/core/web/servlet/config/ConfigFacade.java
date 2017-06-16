package org.fugerit.java.core.web.servlet.config;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.fugerit.java.core.cfg.ConfigurableObject;
import org.fugerit.java.core.io.FileIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogFacade;
import org.fugerit.java.core.util.CheckUtils;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.fugerit.java.core.xml.dom.SearchDOM;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigFacade extends BasicLogObject {

	private static SearchDOM searchDOM = SearchDOM.newInstance( true, true );
	
	private static Logger logger = LogFacade.newLogger( ConfigFacade.class );
	
	public static final String ATT_NAME = "ConfigFacadeAtt";

	private static void logProp( String prop, String value ) {
		log( prop+" : "+value );
	}
	
	private static void log( String message ) {
		logger.info( "[fugerit.ConfigFacade]"+message );
	}
	
	private String secret;
	
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	private ConfigContext configContext;

	public ConfigContext getConfigContext() {
		return configContext;
	}

	public void setConfigContext(ConfigContext configContext) {
		this.configContext = configContext;
	}
	
	private VersionConfig versionConfig = null;
	
	private ModuleConfig moduleConfig = null;
	
	private StatusConfig statusConfig = null;
	
	private CommandConfig commandConfig = null;
	
	public CommandConfig getCommandConfig() {
		return commandConfig;
	}

	public void setCommandConfig(CommandConfig commandConfig) {
		this.commandConfig = commandConfig;
	}

	public VersionConfig getVersionConfig() {
		return versionConfig;
	}

	public void setVersionConfig(VersionConfig versionConfig) {
		this.versionConfig = versionConfig;
	}

	public ModuleConfig getModuleConfig() {
		return moduleConfig;
	}

	public void setModuleConfig(ModuleConfig moduleConfig) {
		this.moduleConfig = moduleConfig;
	}

	public StatusConfig getStatusConfig() {
		return statusConfig;
	}

	public void setStatusConfig(StatusConfig statusConfig) {
		this.statusConfig = statusConfig;
	}

	public static File resolvePath( String path, ServletContext context ) throws FileNotFoundException {
		File file = null;
		file = new File( path );
		if ( !file.exists() ) {
			file = new File( context.getRealPath( "/" ), path );
			if ( !file.exists() ) {
				throw ( new FileNotFoundException( path ) );
			}
		}
		return file;
	}
	
	public static ConfigFacade newFacade( File configFile, ConfigContext configContext ) throws Exception {
		log( "ConfigFacade v 1.0.0 2017-01-02" );
		ConfigFacade facade = new ConfigFacade();
		
		log( "configContext -> "+configContext );
		
		facade.setConfigContext( configContext );
		facade.setModuleConfig( new ModuleConfig() );
		facade.getModuleConfig().setConfigContext( configContext );
		facade.setStatusConfig( new StatusConfig() );
		facade.getStatusConfig().setConfigContext( configContext );
		
		logProp( "configuration read", configFile.getCanonicalPath() );
		Document doc = DOMIO.loadDOMDoc( configFile );
		Element root = doc.getDocumentElement();
		try {
			String secretFile = root.getAttribute( "secret-file" );
			if ( !CheckUtils.isEmpty( secretFile ) ) {
				String secret = FileIO.readString( secretFile );
				if ( secret != null && secret.trim().length() > 0 ) {
					facade.setSecret( secret );
				}
			} else {
				logger.warn( "No secret provided" );
			}
		} catch (Exception e) {
			logger.error( "Error setting secret file", e );
		}
		
		boolean logException = CheckUtils.isTrue( root.getAttribute( "log-exception" ) );
		log( "logException -> "+logException );
		
		// module configuration
		Element moduleConfigListTag = searchDOM.findTag( root , "module-config-list" );
		List<Element> moduleConfigList = searchDOM.findAllTags( moduleConfigListTag , "module-config" );
		Iterator<Element> moduleConfigIt = moduleConfigList.iterator();
		List initLog = new ArrayList();
		while ( moduleConfigIt.hasNext() ) {
			String name = null;
			String type = null;
			String logMessage = null;
			try {
				Element moduleTag = (Element)moduleConfigIt.next();
				Properties moduleAtts = DOMUtils.attributesToProperties( moduleTag );
				name = moduleAtts.getProperty( "name" );
				type = moduleAtts.getProperty( "type" );
				logProp( "configuring module", name );
				logProp( "module class", type );
				ConfigurableObject co = (ConfigurableObject)ClassHelper.newInstance( type );
				if ( co instanceof BasicConfig ) {
					BasicConfig bc = (BasicConfig)co;
					log( "basic config instance -> "+bc );
					bc.setConfigContext( facade.getConfigContext() );
					if ( co instanceof VersionConfig ) {
						facade.setVersionConfig( (VersionConfig) co );
						facade.getVersionConfig().setInitLog( initLog );
					} else if ( co instanceof CommandConfig ) {
						facade.setCommandConfig( (CommandConfig) co );
					}
					facade.getModuleConfig().addModule( name , bc , moduleTag );
				}
				co.configure( moduleTag );
				logProp( "module configured [OK]", name );
				logMessage = "[OK] configured";
				
			} catch ( Throwable t ) {
				log( "module configuration failed [KO] "+t );
				if ( logException ) {
					logger.warn( "exception configuring module : "+name, t );
				}
				logMessage = "[KO] "+t;
			}
			initLog.add( "module:"+name+" type:"+type+" "+logMessage );
		}
		return facade;
	}
	
}
