package org.fugerit.java.core.validator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.xml.ListMapConfig;
import org.fugerit.java.core.fixed.parser.FixedFileFieldBasicValidator;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.fugerit.java.core.xml.dom.DOMProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Validator Catalog is the main configuration item of this simple configuration framework.
 * 
 * You can find a sample configuration at : 
 * fj-core/src/test/resources/core/validator/validator-catalog-test.xml
 * And sample usage in junit test :
 * fj-core/src/test/java/test/org/fugerit/java/core/validator/TestValidatorCatalog.java
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class ValidatorCatalog implements Serializable {

	private final static Logger logger = LoggerFactory.getLogger( ValidatorCatalog.class );
	
	public static final String TAG_CUSTOM_MESSAGE = "custom-messages";

	public static final String DEFAULT_CUSTOM_MESSAGE_LOCALE = "default";
	
	public static final String TAG_VALIDATOR = "validator";
	
	public static final String ATT_TYPE = "type";
	
	public static final String ATT_PARENT = "parent";
	
	public static final String CONF_BUNDLE_PATH = "bundle-path";
	
	public static final String DEFAULT_BUNDLE_PATH = "core.validator.validator";
	
	private Map<Locale, Properties> bundleMap;
	
	private Map<String, Properties> customMessageMap;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853636924461810887L;

	private ListMapConfig<BasicValidator> validators;
	
	private String bundlePath;
	
	public ValidatorCatalog() {
		this.validators = new ListMapConfig<BasicValidator>();
		this.bundleMap = new HashMap<Locale, Properties>();
		this.customMessageMap = new HashMap<String, Properties>();
	}
	
	public ListMapConfig<BasicValidator> getValidators() {
		return validators;
	}
	
	public void configure( Element element ) throws ConfigException {
		try {
			// custom mssages
			NodeList tagsMessages = element.getElementsByTagName( TAG_CUSTOM_MESSAGE );
			for ( int i=0; i<tagsMessages.getLength(); i++ ) {
				Element current = (Element) tagsMessages.item( i );
				String locale = current.getAttribute( "locale" );
				Properties customMessages = new Properties();
				DOMProperty.fill(customMessages, current );
				this.customMessageMap.put(  locale, customMessages );
			}
			// validators
			NodeList tagsValidator = element.getElementsByTagName( TAG_VALIDATOR );
			for ( int k=0; k<tagsValidator.getLength(); k++ ) {
				Element current = (Element) tagsValidator.item( k );
				String type = current.getAttribute( ATT_TYPE );
				if ( StringUtils.isEmpty( type ) ) {
					throw new ConfigException( "Attribute "+ATT_TYPE+" must be set " );
				} else {
					BasicValidator validator = (BasicValidator) ClassHelper.newInstance( type );
					String parent = current.getAttribute( "parent" );
					BasicValidator parentValidator = null;
					if ( StringUtils.isNotEmpty( parent ) ) {
						parentValidator = this.getValidators().get( parent );
						if ( parentValidator == null ) {
							throw new ConfigException( "No parent validator found : "+parent );
						}
					}
					validator.configure( current, parentValidator );
					validator.checkConfig();
					logger.info( "validator : {} -> {}", validator, validator.getParams() );
					this.getValidators().add( validator );
				}
			}
			String bundleConfPath = element.getAttribute( CONF_BUNDLE_PATH );
			if ( StringUtils.isNotEmpty( bundleConfPath ) ) {
				this.bundlePath = bundleConfPath;
			}
		} catch (Exception e) {
			throw new ConfigException( e );
		}
	}
	
	public Properties getBundle( Locale l ) {
		Properties bundle = this.bundleMap.get( l );
		if ( bundle == null ) {
			ResourceBundle temp = FixedFileFieldBasicValidator.newBundle( StringUtils.valueWithDefault( this.bundlePath , DEFAULT_BUNDLE_PATH), l.getLanguage() );
			bundle = new Properties();
			for ( String key : temp.keySet() ) {
				bundle.setProperty( key , temp.getString( key ) );
			}
			Properties customMessages = this.customMessageMap.get( l.toString() );
			if ( customMessages == null ) {
				customMessages = this.customMessageMap.get( l.getLanguage() );
			}
			if ( customMessages == null ) {
				customMessages = this.customMessageMap.get( DEFAULT_CUSTOM_MESSAGE_LOCALE );
			}
			if ( customMessages != null ) {
				bundle.putAll( customMessages );
			}
			this.bundleMap.put( l , bundle );
		}
		return bundle;
	}
	
	public boolean validate( String validatorId, ValidatorResult result, Locale l, String fieldId, String value, String label, Properties params ) throws Exception {
		ValidatorContext context = new ValidatorContext( this , result, l, fieldId, value, label );
		for ( Object key : params.keySet() ) {
			String k = String.valueOf( key );
			context.setAttribute( k , params.getProperty( k ) );
		}
		BasicValidator validator = this.getValidators().get( validatorId );
		return validator.validate( context );
	}
	
	public static ValidatorCatalog init( String path ) throws ConfigException {
		ValidatorCatalog catalog = new ValidatorCatalog();
		init(path, catalog);
		return catalog;
	}
	
	public static void init( String path, ValidatorCatalog catalog ) throws ConfigException {
		try {
			Document doc = DOMIO.loadDOMDoc( StreamHelper.resolveStream(path) );
			catalog.configure( doc.getDocumentElement() );
		} catch (Exception e) {
			throw new ConfigException( e );
		}
	}
	
}
