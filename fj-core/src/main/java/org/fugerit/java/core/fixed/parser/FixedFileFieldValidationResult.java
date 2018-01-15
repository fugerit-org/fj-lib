package org.fugerit.java.core.fixed.parser;

import java.io.Serializable;

public class FixedFileFieldValidationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3685824085601550367L;

	private boolean valid;
	
	private String fieldLabel;
	
	private String message;
	
	private Exception exception;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public FixedFileFieldValidationResult(boolean valid, String fieldLabel, String message, Exception exception) {
		super();
		this.valid = valid;
		this.fieldLabel = fieldLabel;
		this.message = message;
		this.exception = exception;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	
}
