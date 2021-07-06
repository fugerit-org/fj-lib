package org.fugerit.java.core.web.servlet.config;

import java.io.File;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.web.log.helpers.LogObjectServlet;

public class ConfigServlet extends LogObjectServlet {
	
	public static final String OPERATION_INITLOG = "initlog";
	
	public static final String LOAD_TIME = String.valueOf( new java.sql.Timestamp( System.currentTimeMillis() ) );
	
	private ConfigFacade configFacade = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if ( req.getRequestURI().indexOf( "/"+ModuleConfig.OPERATION_RELOAD ) != -1 && this.configFacade.getModuleConfig()!= null ) {
				this.configFacade.getModuleConfig().renderModule( req, resp );
			} else if ( req.getRequestURI().indexOf( "/"+VersionConfig.OPERATION_VERSION ) != -1 && this.configFacade.getVersionConfig() != null ) {
				this.configFacade.getVersionConfig().renderVersion( req, resp );
			} else if ( req.getRequestURI().indexOf( "/"+StatusConfig.OPERATION ) != -1 && this.configFacade.getStatusConfig() != null ) {
				this.configFacade.getStatusConfig().render( req, resp );
			} else if ( req.getRequestURI().indexOf( "/"+CommandConfig.OPERATION_COMMAND ) != -1 && this.configFacade.getCommandConfig() != null ) {
				int index = req.getRequestURI().indexOf( "/"+CommandConfig.OPERATION_COMMAND );
				String command = req.getRequestURI().substring( index+2+CommandConfig.OPERATION_COMMAND.length() );
				String[] params = new String[0];
				this.getLogger().info( "command : '"+command+"'" );
				this.configFacade.getCommandConfig().execute( command, this.getServletContext(), req, resp, params );	
			} else {
				resp.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			}
		} catch (Exception e) {
			this.getLogger().error( "Config servler error"+e , e );
			resp.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
	}

	
	/*
	 * 
	 */
	private static final long serialVersionUID = -2567054666335688813L;

	public static boolean checkSecret( ServletContext context, String secret ) {
		boolean ok = false;
		if ( secret != null && secret.equalsIgnoreCase( (String)context.getAttribute( SECRET_ATT ) ) ) {
			ok = true;
		}
		return ok;
	}
	
	private static final String SECRET_ATT = ConfigServlet.class.getName()+"_ATT_NAME";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init( config );
		String configFilePath = config.getInitParameter( "config-file-path" );
		if ( configFilePath != null ) {
			try {
				File configFile = ConfigFacade.resolvePath( configFilePath , config.getServletContext() );
				this.configFacade = ConfigFacade.newFacade( configFile, new ConfigContext( config.getServletContext() ) );
			} catch (Exception e) {
				throw ( new ServletException( e ) );
			}	
		} else {
			this.configFacade = (ConfigFacade)config.getServletContext().getAttribute( ConfigFacade.ATT_NAME );
		}
	}

	
}
