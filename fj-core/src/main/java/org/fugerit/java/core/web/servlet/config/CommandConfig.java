package org.fugerit.java.core.web.servlet.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.fugerit.java.core.xml.dom.SearchDOM;
import org.w3c.dom.Element;

public class CommandConfig extends BasicConfig {

	/*
	 * 
	 */
	private static final long serialVersionUID = 736509706377526499L;
	
	public static final String OPERATION_COMMAND = "command";
	
	@Override
	public void configure(Properties props) throws ConfigException {
		throw ( new ConfigException( "Unsupported" ) );
	}
	private HashMap<String, CommandBase> cmdMap;
	
	public boolean execute( String commandName, ServletContext context, HttpServletRequest request, HttpServletResponse response, String[] params ) throws Exception {
		CommandBase command = (CommandBase)this.cmdMap.get( commandName );
		return command.execute(context, request, response, params );
	}
	
	@Override
	public void configure(Element tag) throws ConfigException {
		this.getLogger().info( "configure start" );
		try {
			SearchDOM search = SearchDOM.newInstance( true , true );
			List<Element> listContext = search.findAllTags( tag , "command" );
			Iterator<Element> itContext = listContext.iterator();
			this.cmdMap = new HashMap<String, CommandBase>();
			while ( itContext.hasNext() ) {
				Element currentContext = (Element)itContext.next();
				Properties props = DOMUtils.attributesToProperties( currentContext );
				String name = props.getProperty( "name" );
				String type = props.getProperty( "type" );
				this.getLogger().info( "command:"+name+",type:"+type );
				CommandBase command = (CommandBase)ClassHelper.newInstance( type );
				command.setCommand( name );
				this.cmdMap.put( name , command );
			}			
		} catch (Throwable e) {
			throw new ConfigException( e );
		}
		this.getLogger().info( "configure end" );
	}

}
