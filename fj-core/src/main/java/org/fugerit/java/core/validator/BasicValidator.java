package org.fugerit.java.core.validator;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.xml.BasicIdConfigType;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class BasicValidator extends BasicIdConfigType {

	protected static Logger logger = LoggerFactory.getLogger( BasicValidator.class );
	
	public static final String KEY_REQUIRED = "required";
	
	public static final String KEY_OPTION1 = "option1";
	public static final String KEY_OPTION2 = "option2";
	public static final String KEY_OPTION3 = "option3";
	
	public static final String ERROR_KEY_REQUIRED = "error.required";
	
	private boolean required;
	
	private String option1;
	
	private String option2;
	
	private String option3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4497759166570383192L;

	protected String setIfPresent( Properties atts, String key ) {
		String value = atts.getProperty( key );
		if ( StringUtils.isEmpty( value ) ) {
			value = null;
		}
		return value;
	}
	
	public void configure( Element config ) throws ConfigException {
		Properties atts = DOMUtils.attributesToProperties( config );
		String id = atts.getProperty( "id" );
		if ( StringUtils.isNotEmpty( id ) ) {
			this.setId( id );
		} else {
			throw new ConfigException( "no id attribute defined" );
		}
		configure( atts );
	}
	
	public void configure( Properties atts ) throws ConfigException {
		this.required = BooleanUtils.isTrue( atts.getProperty( KEY_REQUIRED ) );
		this.option1 = this.setIfPresent( atts , KEY_OPTION1 );
		this.option2 = this.setIfPresent( atts , KEY_OPTION2 );
		this.option3 = this.setIfPresent( atts , KEY_OPTION3 );
	}
	
	protected String checkOverride( ValidatorContext context, String def, String key ) {
		String res = def;
		if ( context.containsAttribute( key ) ) {
			res = (String) context.getAttribute( key );
		}
		return res;
	}
	
	public boolean validate( ValidatorContext context ) throws Exception {
		boolean valid = this.isOptional() || ( StringUtils.isNotEmpty( context.getValue() ) );
		if ( !valid ) {
			String message = this.formatMessage( context.getBundle() , ERROR_KEY_REQUIRED, context.getLabel() );
			context.getResult().getErrors().add( message );
		}
		return valid;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isOptional() {
		return !this.isRequired();
	}

	public String getOption1() {
		return option1;
	}

	public String getOption2() {
		return option2;
	}

	public String getOption3() {
		return option3;
	}
	
	@Override
	public String toString() {
		return "Validator[id:"+this.getId()+",type:"+this.getClass().getName()+"]";
	}
	
	public String formatMessage( ResourceBundle bundle, String key, String... params ) {
		String baseValue = bundle.getString( key );
		Object[] p = (Object[])params;
		return MessageFormat.format( baseValue , p );
	}
	
}
