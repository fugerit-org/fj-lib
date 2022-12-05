/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.cfg;

import org.fugerit.java.core.lang.ex.CodeException;

/**
 * <p>Exception for handling unexpected situations during configuration.</p>
 *
 * <p>Config exception is able to wrap a code.</p>
 *
 * @author Fugerit
 *
 */
public class ConfigException extends CodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6673695755101705239L;
	
	/**
	 * <p>Default value for the code field in a ConfigException.</p>
	 */
	public static final int DEFAULT_CODE = CodeException.DEFAULT_CODE;

	public ConfigException() {
		super();
	}

	public ConfigException(int code) {
		super(code);
	}

	public ConfigException(String message, int code) {
		super(message, code);
	}

	public ConfigException(String message, Throwable cause, int code) {
		super(message, cause, code);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause, int code) {
		super(cause, code);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}
	
}
