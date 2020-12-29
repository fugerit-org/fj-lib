package org.fugerit.java.core.validator;

import java.util.Properties;
import java.util.regex.Pattern;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class ValidatorRegex extends BasicValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5690219274192449044L;

	public static final String KEY_REGEX = "regex";
	
	public static final String ERROR_KEY_REGEX = "error.regex";

	private String regex;
	
	public String getRegex() {
		return regex;
	}
	
	@Override
	public void checkConfig() throws ConfigException {
		super.checkConfig();
		if ( StringUtils.isEmpty( this.getRegex() ) ) {
			throw new ConfigException( "You must configure a 'regex' attribute for this validator "+this.getId() );
		}
	}

	@Override
	public void configure( Properties atts ) throws ConfigException {
		super.configure(atts);
		String regex = atts.getProperty( KEY_REGEX );
		if (  StringUtils.isNotEmpty( regex ) ) {
			this.regex = regex;
		}
	}

	@Override
	public boolean validate(ValidatorContext context) throws Exception {
		boolean valid = super.validate(context);
		if ( !Pattern.matches( this.getRegex() , context.getValue() ) ) {
			valid = false;
			String message = this.formatMessage( context.getBundle() , ERROR_KEY_REGEX, context.getLabel(), context.getValue(), StringUtils.valueWithDefault( this.getInfo(), this.getRegex() ) );
			context.getResult().getErrors().add( message );
		}
		return valid;
	}

}
