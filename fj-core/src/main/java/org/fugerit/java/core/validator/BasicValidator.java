package org.fugerit.java.core.validator;

import java.text.MessageFormat;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.xml.BasicIdConfigType;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.xml.dom.DOMProperty;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class BasicValidator extends BasicIdConfigType {

	protected static Logger logger = LoggerFactory.getLogger( BasicValidator.class );
	
	public static final String KEY_REQUIRED = "required";
	
	public static final String KEY_MINLENGTH = "minLength";
	public static final String KEY_MAXLENGTH = "maxLength";
	
	public static final int NO_LENGTH_CONSTRAINT = -1;
	
	public static final String KEY_INFO = "info";
	
	public static final String ERROR_KEY_REQUIRED = "error.required";
	public static final String ERROR_KEY_LENGTH_MIN = "error.length.min";
	public static final String ERROR_KEY_LENGTH_MAX = "error.length.max";
	
	private boolean required = true;
	
	private int minLength = NO_LENGTH_CONSTRAINT;
	
	private int maxLength = NO_LENGTH_CONSTRAINT;
	
	private String info;
	
	private Element config;
	
	private Properties params;
	
	private BasicValidator parent;
	
	public BasicValidator() {
		this.params = new Properties();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4497759166570383192L;
	
	public void configure( Element config, BasicValidator parent ) throws ConfigException {
		if ( parent != null ) {
			logger.info( "Extends : {}", parent );
			this.parent = parent;
			this.configure( parent.getConfig() );
		}
		this.configure( config );
	}
	
	public void checkConfig() throws ConfigException{
		// do nothing implementation : subclasses should implement it, if needed
	}
	
	public void configure( Element config ) throws ConfigException {
		this.config = config;
		Properties atts = DOMUtils.attributesToProperties( config );
		String id = atts.getProperty( "id" );
		if ( StringUtils.isNotEmpty( id ) ) {
			this.setId( id );
		} else {
			throw new ConfigException( "no id attribute defined" );
		}
		DOMProperty.fill( atts , config );
		configure( atts );
	}
	
	public void configure( Properties atts ) throws ConfigException {
		String req = atts.getProperty( KEY_REQUIRED );
		if ( StringUtils.isNotEmpty( req ) ) {
			this.required = BooleanUtils.isTrue( req );
		}
		String min = atts.getProperty( KEY_MINLENGTH );
		if ( StringUtils.isNotEmpty( min ) ) {
			this.minLength = Integer.parseInt( min );
		}
		String max = atts.getProperty( KEY_MAXLENGTH );
		if ( StringUtils.isNotEmpty( max ) ) {
			this.maxLength = Integer.parseInt( max );
		}
		String infoLocal = atts.getProperty( KEY_INFO );
		if ( StringUtils.isNotEmpty( infoLocal ) ) {
			this.info = infoLocal;
		}		
		this.getParams().putAll( atts );
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
			context.getResult().addError( context.getFieldId(), message );
		}
		if ( valid && StringUtils.isNotEmpty( context.getValue() ) ) {
			int length = context.getValue().length();
			if ( this.getMinLength() != NO_LENGTH_CONSTRAINT && length < this.getMinLength() ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_LENGTH_MIN, context.getLabel(), String.valueOf( length ), String.valueOf( this.getMinLength() ) );
				context.getResult().addError( context.getFieldId(), message );
			}
			if ( this.getMaxLength() != NO_LENGTH_CONSTRAINT && length > this.getMaxLength() ) {
				valid = false;
				String message = this.formatMessage( context.getBundle() , ERROR_KEY_LENGTH_MAX, context.getLabel(), String.valueOf( length ), String.valueOf( this.getMaxLength() ) );
				context.getResult().addError( context.getFieldId(), message );
			}
		}
		return valid;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isOptional() {
		return !this.isRequired();
	}
	
	public int getMinLength() {
		return minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}
	
	public String getInfo() {
		return info;
	}
	
	protected Element getConfig() {
		return config;
	}

	protected BasicValidator getParent() {
		return parent;
	}

	public Properties getParams() {
		return params;
	}

	@Override
	public String toString() {
		return "Validator[id:"+this.getId()+",type:"+this.getClass().getName()+"]";
	}
	
	public String formatMessage( Properties bundle, String key, String... params ) {
		String baseValue = bundle.getProperty( key );
		Object[] p = (Object[])params;
		return MessageFormat.format( baseValue , p );
	}
	
}
