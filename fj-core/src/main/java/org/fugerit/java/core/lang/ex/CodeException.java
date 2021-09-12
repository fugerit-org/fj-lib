package org.fugerit.java.core.lang.ex;

public class CodeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8144962171201460491L;
	
	public static final int DEFAULT_CODE = -1;
	
	private final int code;

	public int getCode() {
		return code;
	}
	
	public CodeException() {
		this( DEFAULT_CODE );
	}

	public CodeException(String message, Throwable cause) {
		this(message, cause, DEFAULT_CODE);
	}

	public CodeException(String message) {
		this(message, DEFAULT_CODE);
	}

	public CodeException(Throwable cause) {
		this(cause, DEFAULT_CODE);
	}

	public CodeException( int code ) {
		super();
		this.code = code;
	}

	public CodeException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}

	public CodeException(String message, int code) {
		super(message);
		this.code = code;
	}

	public CodeException(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}

}
