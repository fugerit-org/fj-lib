package org.fugerit.java.core.fixed.parser;

import java.text.MessageFormat;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class FixedFileFieldBasicValidator extends XMLConfigurableObject implements FixedFileFieldValidator {

	public static final String DEFAULT_BUNDLE_PATH = "core.fixed.parser.validator";
	
	public static ResourceBundle newBundle( String locale ) {
		return newBundle( DEFAULT_BUNDLE_PATH, locale );		
	}
	
	public static String messageFormatWorker( ResourceBundle bundle, String errorKey, String fieldLabel, String fieldValue, int rowNumber, int colNumber, String addInfo ) {
		String[] args = {
			String.valueOf( rowNumber ),
			fieldLabel,
			fieldValue,
			addInfo
		};
		String baseError = bundle.getString( errorKey );
		MessageFormat mf = new MessageFormat( baseError );
		String message = mf.format( args );
		log.trace( "message colNumber:{} -> {}", colNumber, message );
		return message;
}
	
	public static ResourceBundle newBundle( String bundlePath, String locale ) {
		// locale setting
		Locale loc = Locale.getDefault();
		if ( StringUtils.isNotEmpty( locale ) ) {
			loc = LocaleHelper.convertLocale( locale , LocaleHelper.USE_DEFAULT_ON_ERROR );
		}
		return ResourceBundle.getBundle( bundlePath, loc );		
	}
	
	protected static final Logger logger = LoggerFactory.getLogger( FixedFileFieldBasicValidator.class );
	
	public static final String ATT_NAME_ID = "id";
	public static final String ATT_NAME_REGEX = "regex";
	public static final String ATT_NAME_LOCALE = "locale";
	public static final String ATT_NAME_REQUIRED = "required";

	private transient ResourceBundle bundle;
	
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

	protected String defaultFormatMessage( String errorKey, String fieldLabel, String fieldValue, int rowNumber, int colNumber, String addInfo ) {
			return messageFormatWorker( this.getBundle() , errorKey, fieldLabel, fieldValue, rowNumber, colNumber, addInfo);
	}
	
	protected FixedFileFieldValidationResult checkRequired(String fieldLabel, String fieldValue, int rowNumber, int colNumber ) {
		boolean valid = true;
		String message = null;
		Exception exception = null;
		if ( fieldValue == null ) {
			fieldValue = "";
		}
		try {
			if ( StringUtils.isEmpty( fieldValue ) && this.isRequired() ) {
				message = defaultFormatMessage( "error.required", fieldLabel, fieldValue, rowNumber, colNumber, "" );
				valid = false;
			}
		} catch (Exception e) {
			valid = false;
			message = "Validation exception "+e.getMessage();
			exception = e;
		}
		return new FixedFileFieldValidationResult( valid, fieldLabel, message, exception, rowNumber, colNumber );
	}

	protected void configure( Element tag, String bundlePath ) throws ConfigException {
		ConfigException.apply( () -> {
			String idLocal = tag.getAttribute( ATT_NAME_ID );
			logger.info( "id {} -> '{}'", ATT_NAME_ID, idLocal );
			String locale = tag.getAttribute( ATT_NAME_LOCALE );
			logger.info( "locale {} -> '{}", ATT_NAME_LOCALE, locale );
			String req = tag.getAttribute( ATT_NAME_REQUIRED );
			this.required = BooleanUtils.isTrue( req );
			this.bundle = newBundle(bundlePath, locale);			
		} );
	}

}
