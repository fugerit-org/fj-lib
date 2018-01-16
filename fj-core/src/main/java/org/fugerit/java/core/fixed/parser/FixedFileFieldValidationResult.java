package org.fugerit.java.core.fixed.parser;

import java.io.Serializable;

public class FixedFileFieldValidationResult implements Serializable {

	/**
	 * Row or col number index not set
	 */
	public static final int ROW_COL_UNSET = -1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3685824085601550367L;

	private boolean valid;
	
	private String fieldLabel;
	
	private String message;
	
	private Exception exception;

	private int row;
	
	private int col;
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

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

	public FixedFileFieldValidationResult(boolean valid, String fieldLabel, String message, Exception exception ) {
		this( valid, fieldLabel, message, exception, ROW_COL_UNSET, ROW_COL_UNSET );
	}
	
	public FixedFileFieldValidationResult(boolean valid, String fieldLabel, String message, Exception exception, int row, int col ) {
		super();
		this.valid = valid;
		this.fieldLabel = fieldLabel;
		this.message = message;
		this.exception = exception;
		this.row = row;
		this.col = col;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	
}
