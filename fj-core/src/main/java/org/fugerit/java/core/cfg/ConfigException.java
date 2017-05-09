/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.cfg;

/**
 * <p>Exception for handling unexpected situations during configuration.</p>
 *
 * <p>Config exception is able to wrap a code.</p>
 *
 * @author Fugerit
 *
 */
public class ConfigException extends Exception {

	/**
	 * <p>Default value for the code field in a ConfigException.</p>
	 */
	public static final int DEFAULT_CODE = -1;
	
	private int code = DEFAULT_CODE;
	
	/*
	 * 
	 */
	private static final long serialVersionUID = -4205246051306630691L;

	public ConfigException() {
		super();
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

	public ConfigException(int code) {
		super();
		this.code = code;
	}

	public ConfigException(String message, int code) {
		super(message);
		this.code = code;
	}

	public ConfigException(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}

	public ConfigException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return super.toString()+"[code:"+this.code+"]";
	}

}
