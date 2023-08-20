package org.fugerit.java.core.cfg;

import org.fugerit.java.core.lang.ex.CodeException;
import org.fugerit.java.core.lang.ex.CodeRuntimeException;
import org.fugerit.java.core.lang.ex.ExConverUtils;

public class ConfigRuntimeException extends CodeRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6673695755105239L;
	
	/**
	 * <p>Default value for the code field in a ConfigRuntimeException.</p>
	 */
	public static final int DEFAULT_CODE = CodeException.DEFAULT_CODE;

	public ConfigRuntimeException() {
		super();
	}

	public ConfigRuntimeException(int code) {
		super(code);
	}

	public ConfigRuntimeException(String message, int code) {
		super(message, code);
	}

	public ConfigRuntimeException(String message, Throwable cause, int code) {
		super(message, cause, code);
	}

	public ConfigRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigRuntimeException(String message) {
		super(message);
	}

	public ConfigRuntimeException(Throwable cause, int code) {
		super(cause, code);
	}

	public ConfigRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public static ConfigRuntimeException stadardExceptionWrapping( Exception e ) throws ConfigRuntimeException {
		throw convertEx( "Configuration error", e );
	}
	
	public static ConfigRuntimeException convertEx( String baseMessage, Exception e ) {
		ConfigRuntimeException res = null;
		if ( e instanceof ConfigRuntimeException ) {
			res = (ConfigRuntimeException)e;
		} else {
			res = new ConfigRuntimeException( ExConverUtils.defaultMessage(baseMessage, e), e );
		}
		return res;
	}

	public static ConfigRuntimeException convertExMethod( String method, Exception e ) {
		return convertEx( ExConverUtils.defaultMethodMessage(method), e );
	}
	
	public static ConfigRuntimeException convertEx( Exception e ) {
		return convertEx( ExConverUtils.DEFAULT_CAUSE_MESSAGE, e );
	}
	
}
