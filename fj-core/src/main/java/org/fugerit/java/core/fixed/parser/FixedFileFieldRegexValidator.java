package org.fugerit.java.core.fixed.parser;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.w3c.dom.Element;

public class FixedFileFieldRegexValidator extends XMLConfigurableObject implements FixedFileFieldValidator {

	public static final String ATT_NAME_REGEX = "regex";
	public static final String ATT_NAME_LOCALE = "locale";

	private ResourceBundle bundle;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5571414972052579008L;

	private String regex;
	
	@Override
	public FixedFileFieldValidationResult checkField(String fieldLabel, String fieldValue, int rowNumber) {
		boolean valid = true;
		String message = null;
		Exception exception = null;
		try {
			if ( StringUtils.isNotEmpty( fieldValue ) && !fieldValue.matches( this.regex ) ) {
				String[] args = {
					String.valueOf( rowNumber ),
					fieldLabel,
					fieldValue,
					this.regex
				};
				String baseError = this.bundle.getString( "error.regex" );
				MessageFormat mf = new MessageFormat( baseError );
				message = mf.format( args );
				valid = false;
			}	
		} catch (Exception e) {
			valid = false;
			message = "Validation exception "+e.getMessage();
			exception = e;
		}
		return new FixedFileFieldValidationResult( valid, fieldLabel, message, exception );
	}

	@Override
	public void configure( Element tag ) throws ConfigException {
		String config = tag.getAttribute( ATT_NAME_REGEX );
		String locale = tag.getAttribute( ATT_NAME_LOCALE );
		if ( StringUtils.isEmpty( config ) ) {
			throw new ConfigException( ATT_NAME_REGEX+" is mandatory attribute" );
		} else {
			this.regex = config;
		}
		Locale loc = Locale.getDefault();
		if ( StringUtils.isNotEmpty( locale ) ) {
			loc = Locale.forLanguageTag( locale );
		}
		this.bundle = ResourceBundle.getBundle( "core.fixed.parser.validator", loc );
	}
	
	

}
