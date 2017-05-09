package org.fugerit.java.core.web.servlet.config;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogFacade;
import org.slf4j.Logger;

public class ConfigListener extends BasicLogObject implements ServletContextListener {

	private static Logger logger = LogFacade.newLogger( ConfigListener.class );
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info( "contextInitialized() START" );
		ServletContext context = event.getServletContext();
		String configFilePath = context.getInitParameter( "config-file-path" );
		try {
			File configFile = ConfigFacade.resolvePath( configFilePath , context );
			ConfigFacade configFacade = ConfigFacade.newFacade( configFile, new ConfigContext( context ) );
			context.setAttribute( ConfigFacade.ATT_NAME , configFacade );
			logger.info( "contextInitialized() OK" );
		} catch (Throwable t) {
			logger.error( "contextInitialized() init error", t );
		}
	}

}
