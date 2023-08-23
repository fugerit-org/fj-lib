package org.fugerit.java.core.fixed.parser;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.w3c.dom.Element;

public class FixedFileFieldRegexValidator extends FixedFileFieldBasicValidator implements FixedFileFieldValidator {

	public static final String ATT_NAME_REGEX = "regex";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5571414972052579008L;

	private String regex;
	
	public String getRegex() {
		return regex;
	}

	
	@Override
	public FixedFileFieldValidationResult checkField(String fieldLabel, String fieldValue, int rowNumber, int colNumber ) {
		FixedFileFieldValidationResult result = null;
		boolean valid = true;
		String message = null;
		Exception exception = null;
		if ( fieldValue == null ) {
			fieldValue = "";
		}
		try {
			FixedFileFieldValidationResult checkRequired = super.checkRequired(fieldLabel, fieldValue, rowNumber, colNumber);
			if ( checkRequired.isValid() && StringUtils.isNotEmpty( fieldValue ) ) {
				if ( !fieldValue.matches( this.getRegex() ) ) {
					message = super.defaultFormatMessage( "error.regex", fieldLabel, fieldValue, rowNumber, colNumber, this.getRegex() );
					valid = false;	
				}
			} else {
				result = checkRequired;
			}
		} catch (Exception e) {
			valid = false;
			message = "Validation exception "+e.getMessage();
			exception = e;
		}
		if ( result == null ) {
			result = new FixedFileFieldValidationResult( valid, fieldLabel, message, exception, rowNumber, colNumber );
		}
		return result;
	}

	@Override
	public void configure( Element tag ) throws ConfigException {
		String config = tag.getAttribute( ATT_NAME_REGEX );
		logger.info( "config {} -> '{}'", ATT_NAME_REGEX, config );
		if ( StringUtils.isEmpty( config ) ) {
			throw new ConfigException( ATT_NAME_REGEX+" is mandatory attribute" );
		} else {
			this.regex = config;
		}
		super.configure( tag, "core.fixed.parser.validator" );
	}
	
	

}
