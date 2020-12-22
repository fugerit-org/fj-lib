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
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ValidatorCatalog implements Serializable {

	private final static Logger logger = LoggerFactory.getLogger( ValidatorCatalog.class );
	
	public static final String TAG_VALIDATOR = "validator";
	
	public static final String ATT_TYPE = "type";
	
	public static final String CONF_BUNDLE_PATH = "bundle-path";
	
	public static final String DEFAULT_BUNDLE_PATH = "core.validator.validator";
	
	private Map<Locale, ResourceBundle> bundleMap;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853636924461810887L;

	private ListMapConfig<BasicValidator> validators;
	
	private String bundlePath;
	
	public ValidatorCatalog() {
		this.validators = new ListMapConfig<BasicValidator>();
		this.bundleMap = new HashMap<Locale, ResourceBundle>();
	}
	
	public ListMapConfig<BasicValidator> getValidators() {
		return validators;
	}
	
	public void configure( Element element ) throws ConfigException {
		try {
			NodeList tagsValidator = element.getElementsByTagName( TAG_VALIDATOR );
			for ( int k=0; k<tagsValidator.getLength(); k++ ) {
				Element current = (Element) tagsValidator.item( k );
				String type = current.getAttribute( ATT_TYPE );
				if ( StringUtils.isEmpty( type ) ) {
					throw new ConfigException( "Attribute "+ATT_TYPE+" must be set " );
				} else {
					BasicValidator validator = (BasicValidator) ClassHelper.newInstance( type );
					validator.configure( current );
					logger.info( "validator : {}", validator );
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
	
	public ResourceBundle getBundle( Locale l ) {
		ResourceBundle bundle = this.bundleMap.get( l );
		if ( bundle == null ) {
			bundle = FixedFileFieldBasicValidator.newBundle( StringUtils.valueWithDefault( this.bundlePath , DEFAULT_BUNDLE_PATH), l.getLanguage() );
			this.bundleMap.put( l , bundle );
		}
		return bundle;
	}
	
	public boolean validate( String validatorId, ValidatorResult result, Locale l, String value, String label, Properties params ) throws Exception {
		ValidatorContext context = new ValidatorContext( this , result, l, value, label);
		for ( Object key : params.keySet() ) {
			String k = String.valueOf( key );
			context.setAttribute( k , params.getProperty( k ) );
		}
		BasicValidator validator = this.getValidators().get( validatorId );
		return validator.validate( context );
	}
	
	public static ValidatorCatalog init( String path ) throws ConfigException {
		ValidatorCatalog catalog = new ValidatorCatalog();
		try {
			Document doc = DOMIO.loadDOMDoc( ClassHelper.loadFromDefaultClassLoader( path ) );
			catalog.configure( doc.getDocumentElement() );
		} catch (Exception e) {
			throw new ConfigException( e );
		}
		return catalog;
	}
	
}
