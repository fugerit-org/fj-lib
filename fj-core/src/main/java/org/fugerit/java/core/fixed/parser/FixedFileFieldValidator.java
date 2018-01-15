package org.fugerit.java.core.fixed.parser;

import org.fugerit.java.core.cfg.ConfigurableObject;

public interface FixedFileFieldValidator extends ConfigurableObject {

	public FixedFileFieldValidationResult checkField( String fieldLabel, String fieldValue, int rowNumber );
	
}
