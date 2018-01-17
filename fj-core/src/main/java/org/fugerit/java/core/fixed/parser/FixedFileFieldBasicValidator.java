package org.fugerit.java.core.fixed.parser;

import java.util.Locale;
import java.util.ResourceBundle;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.LocaleHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class FixedFileFieldBasicValidator extends XMLConfigurableObject implements FixedFileFieldValidator {

	protected static final Logger logger = LoggerFactory.getLogger( FixedFileFieldBasicValidator.class );
	
	public static final String ATT_NAME_ID = "id";
	public static final String ATT_NAME_REGEX = "regex";
	public static final String ATT_NAME_LOCALE = "locale";
	public static final String ATT_NAME_REQUIRED = "required";

	private ResourceBundle bundle;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5571414972052579008L;

	private boolean required;
	
	private String id;
	
	public boolean isRequired() {
		return required;
	}

	public String getId() {
		return id;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	@Override
	public abstract FixedFileFieldValidationResult checkField(String fieldLabel, String fieldValue, int rowNumber, int colNumber );

	protected void configure( Element tag, String bundlePath ) throws ConfigException {
		String id = tag.getAttribute( ATT_NAME_ID );
		logger.info( "id "+ATT_NAME_ID+" -> '"+id+"'" );
		String locale = tag.getAttribute( ATT_NAME_LOCALE );
		logger.info( "locale "+ATT_NAME_LOCALE+" -> '"+locale+"'" );
		String req = tag.getAttribute( ATT_NAME_REQUIRED );
		this.required = BooleanUtils.isTrue( req );
		// locale setting
		Locale loc = Locale.getDefault();
		if ( StringUtils.isNotEmpty( locale ) ) {
			loc = LocaleHelper.convertLocale( locale , LocaleHelper.USE_DEFAULT_ON_ERROR );
		}
		this.bundle = ResourceBundle.getBundle( bundlePath, loc );
	}
	
	@Override
	public abstract void configure( Element tag ) throws ConfigException;
	
	

}
