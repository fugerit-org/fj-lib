package org.fugerit.java.core.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidatorResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5473162078733130133L;

	private List<String> errors;
	
	private List<String> warnings;
	
	public ValidatorResult() {
		this.errors = new ArrayList<String>();
		this.warnings = new ArrayList<String>();
	}

	public List<String> getErrors() {
		return errors;
	}

	public List<String> getWarnings() {
		return warnings;
	}

}
