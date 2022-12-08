package org.fugerit.java.core.cfg;

import org.fugerit.java.core.lang.ex.CodeException;
import org.fugerit.java.core.lang.ex.CodeRuntimeException;

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
	
}
