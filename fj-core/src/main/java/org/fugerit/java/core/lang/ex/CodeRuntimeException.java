package org.fugerit.java.core.lang.ex;


public class CodeRuntimeException extends RuntimeException implements CodeEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = -814496260491L;
	
	/**
	 * <p>Default value for the code field in a ConfigRuntimeException.</p>
	 */
	public static final int DEFAULT_CODE = CodeException.DEFAULT_CODE;
	
	private final int code;

	@Override
	public int getCode() {
		return code;
	}
	
	public CodeRuntimeException() {
		this( DEFAULT_CODE );
	}

	public CodeRuntimeException(String message, Throwable cause) {
		this(message, cause, DEFAULT_CODE);
	}

	public CodeRuntimeException(String message) {
		this(message, DEFAULT_CODE);
	}

	public CodeRuntimeException(Throwable cause) {
		this(cause, DEFAULT_CODE);
	}

	public CodeRuntimeException( int code ) {
		super();
		this.code = code;
	}

	public CodeRuntimeException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}

	public CodeRuntimeException(String message, int code) {
		super(message);
		this.code = code;
	}

	public CodeRuntimeException(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}
	
}
