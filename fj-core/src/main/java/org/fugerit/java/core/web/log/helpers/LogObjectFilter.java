package org.fugerit.java.core.web.log.helpers;

import javax.servlet.Filter;

import org.fugerit.java.core.log.BasicLogObject;

public abstract class LogObjectFilter extends BasicLogObject implements Filter {

	public void logInit( String param, String value ) {
		this.logInit( param+" : '"+value+"'" );
	}
	
	public void logInit( String message ) {
		this.getLogger().info( "[INIT]"+message );
	}
	
}
