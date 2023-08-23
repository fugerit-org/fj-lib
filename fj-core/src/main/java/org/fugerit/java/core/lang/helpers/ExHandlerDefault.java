package org.fugerit.java.core.lang.helpers;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExHandlerDefault implements ExHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 501053739052010770L;

	private static final Logger logger = LoggerFactory.getLogger( ExHandlerDefault.class );

	public static final ExHandler INSTANCCE = new ExHandlerDefault();
	
	protected void worker( String method, Exception e ) {
		logger.error( "method:{}, exception:{}", method, (e!=null) ? e.toString() : null );
	}
	
	@Override
	public void fatal(Exception e) {
		this.worker( "fatal" , e );
	}

	@Override
	public void error(Exception e) {
		this.worker( "error" , e );
	}

	@Override
	public void warning(Exception e) {
		this.worker( "warning" , e );
	}

}
