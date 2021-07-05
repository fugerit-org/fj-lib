package org.fugerit.java.core.web.log.helpers;

import javax.servlet.http.HttpServlet;

import org.fugerit.java.core.log.LogFacade;
import org.fugerit.java.core.log.LogObject;
import org.slf4j.Logger;

/*
 * 
 *
 * @author Fugerit
 *
 */
public class LogObjectServlet extends HttpServlet implements LogObject {

	public void logInit( String message ) {
		this.getLogger().info( "[INIT]"+message );
	}	
	
	public LogObjectServlet() {
		this.log = LogFacade.newLogger( this );
	}
	
	private Logger log;

	@Override
	public Logger getLogger() {
		return this.log;
	}

	/*
	 * 
	 */
	private static final long serialVersionUID = 8122658652733496521L;

}
