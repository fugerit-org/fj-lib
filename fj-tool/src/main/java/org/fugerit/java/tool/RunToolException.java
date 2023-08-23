package org.fugerit.java.tool;

import org.fugerit.java.core.lang.ex.ExConverUtils;

public class RunToolException extends Exception {

	private static final long serialVersionUID = -5290015622577525291L;

	public RunToolException() {
		super();
	}

	public RunToolException(String message, Throwable cause) {
		super(message, cause);
	}

	public RunToolException(String message) {
		super(message);
	}

	public RunToolException(Throwable cause) {
		super(cause);
	}
	
	public static RunToolException stadardExceptionWrapping( Exception e ) throws RunToolException {
		throw convertEx( "Configuration error", e );
	}
	
	public static RunToolException convertEx( String baseMessage, Exception e ) {
		RunToolException res = null;
		if ( e instanceof RunToolException ) {
			res = (RunToolException)e;
		} else {
			res = new RunToolException( ExConverUtils.defaultMessage(baseMessage, e), e );
		}
		return res;
	}

	public static RunToolException convertExMethod( String method, Exception e ) {
		return convertEx( ExConverUtils.defaultMethodMessage(method), e );
	}
	
	public static RunToolException convertEx( Exception e ) {
		return convertEx( ExConverUtils.DEFAULT_CAUSE_MESSAGE, e );
	}
	
}
