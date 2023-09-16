package org.fugerit.java.core.util.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.fugerit.java.core.lang.helpers.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleMapI18N extends HelperI18N {

	private static final Logger logger = LoggerFactory.getLogger( BundleMapI18N.class );
	
	private static void addBundle(  Map<String, ResourceBundle> bundleMap, String baseName, String lang ) {
		Locale langTag = Locale.forLanguageTag( lang );
		ResourceBundle bundle = ResourceBundle.getBundle( baseName, langTag, new XMLResourceBundleControl() );
		logger.info( "adding bundle, lang : {} -> {}", lang, langTag );
		bundleMap.put( lang , bundle );
	}
	
	private static void createBundleMap( Map<String, ResourceBundle> bundleMap, String baseName, String defaultLanguge, String... altLocale ) {
		addBundle(bundleMap, baseName, defaultLanguge);
		for ( int k=0; k<altLocale.length; k++ ) {
			addBundle(bundleMap, baseName, altLocale[k]);
		}
	}
	
	public static HelperI18N newHelperI18N( String baseName, String defaultLanguge,  String... altLocale ) {
		return new BundleMapI18N(baseName, defaultLanguge, altLocale);
	}
	
	public BundleMapI18N(String baseName, String defaultLanguge, String... altLocale) {
		super(baseName, defaultLanguge, altLocale);
		this.bundleMap = new HashMap<>();
		createBundleMap(this.bundleMap, baseName, defaultLanguge, altLocale);	
	}
	
	public Map<String, ResourceBundle> getBundleMap() {
		return bundleMap;
	}

	public ResourceBundle findBundle( String lang ) {
		ResourceBundle bundle = null;
		if ( StringUtils.isNotEmpty( lang ) ) {
			bundle = this.getBundleMap().get( lang );
		} else {
			bundle = this.getBundleMap().get( this.getDefaultLanguage() );
		}
		return bundle;
	}
	
	
	private Map<String, ResourceBundle> bundleMap;

	@Override
	public String resolveString(String lang, String key) {
		String message = null;
		try {
			ResourceBundle bundle = this.findBundle( lang );
			if ( bundle != null ) {
				message = bundle.getString( key );
			}
		} catch (MissingResourceException mre) {
			logger.warn( "Property not found : {} (lang:{})", key, lang );
		}
		return message;
	}
	
	
	
}
