package org.fugerit.java.core.validator;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.xml.BasicIdConfigType;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.xml.dom.DOMProperty;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public class BasicValidator extends BasicIdConfigType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4497759166570383192L;
	
	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END	
	
	public static final String KEY_REQUIRED = "required";
	
	public static final String KEY_MINLENGTH = "minLength";
	public static final String KEY_MAXLENGTH = "maxLength";
	
	public static final int NO_LENGTH_CONSTRAINT = -1;
	
	public static final String KEY_INFO = "info";
	
	public static final String ERROR_KEY_REQUIRED = "error.required";
	public static final String ERROR_KEY_LENGTH_MIN = "error.length.min";
	public static final String ERROR_KEY_LENGTH_MAX = "error.length.max";
	
	@Getter private boolean required = true;
	
	@Getter private int minLength = NO_LENGTH_CONSTRAINT;
	
	@Getter private int maxLength = NO_LENGTH_CONSTRAINT;
	
	@Getter private String info;
	
	@Getter private Element config;
	
	@Getter private Properties params;
	
	@Getter private BasicValidator parent;

	public boolean isOptional() {
		return !this.isRequired();
	}
		
	public BasicValidator() {
		this.params = new Properties();
	}

	public void configure( Element config, BasicValidator parent ) throws ConfigException {
		if ( parent != null ) {
			log.info( "Extends : {}", parent );
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
	
	public String formatMessage( Properties bundle, String key, String... params ) {
		String baseValue = bundle.getProperty( key );
		Object[] p = (Object[])params;
		return MessageFormat.format( baseValue , p );
	}
	
}
