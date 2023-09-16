package org.fugerit.java.core.fixed.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FixedFileFieldMap extends HashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4410493087160204864L;

	@Override
	public int hashCode() {
		// super class implementation is ok
		return super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		// super class implementation is ok
		return super.equals(o);
	}
	
	private List<FixedFileFieldValidationResult> validationErrors;

	public List<FixedFileFieldValidationResult> getValidationErrors() {
		return validationErrors;
	}
	
	public FixedFileFieldMap( int recordLength, int currentRow ) {
		super();
		this.validationErrors = new ArrayList<>();
		this.recordLength = recordLength;
		this.currentRow = currentRow;
	}
	
	private int currentRow;
	
	private int recordLength;

	public int getRecordLength() {
		return recordLength;
	}

	public int getCurrentRow() {
		return currentRow;
	}
	
}
