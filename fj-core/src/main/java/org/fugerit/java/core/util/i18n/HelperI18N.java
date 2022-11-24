package org.fugerit.java.core.util.i18n;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.fugerit.java.core.lang.helpers.StringUtils;

public class HelperI18N {
	
	private static void createBundleMap( Map<String, ResourceBundle> bundleMap, String baseName, String defaultLanguge, String... altLocale ) {
		bundleMap.put( defaultLanguge , ResourceBundle.getBundle( baseName, Locale.forLanguageTag( defaultLanguge ), new XMLResourceBundleControl() ) );
		for ( int k=0; k<altLocale.length; k++ ) {
			bundleMap.put( altLocale[k] , ResourceBundle.getBundle( baseName, Locale.forLanguageTag( altLocale[k] ), new XMLResourceBundleControl() ) );
		}
	}
	
	public static HelperI18N newHelperI18N( String baseName, String defaultLanguge,  String... altLocale ) {
		HelperI18N helper = new HelperI18N(baseName, defaultLanguge, altLocale);
		return helper;
	}
	
	protected HelperI18N( String baseName, String defaultLanguge,  String... altLocale ) {
		this.defaultLanguage = defaultLanguge;
		this.altLocale = altLocale;
		if ( this.altLocale == null ) {
			this.altLocale = new String[0];
		}
		this.bundleMap = new HashMap<String, ResourceBundle>();
		createBundleMap(this.bundleMap, baseName, defaultLanguge, altLocale);
	}

	private String defaultLanguage;
	
	private String[] altLocale;
	
	private Map<String, ResourceBundle> bundleMap;
	
	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public List<String> getAltLocale() {
		return Arrays.asList( this.altLocale );
	}

	public Map<String, ResourceBundle> getBundleMap() {
		return bundleMap;
	}

	public ResourceBundle findBundle( String lang ) {
		ResourceBundle bundle = this.bundleMap.get( this.getDefaultLanguage() );
		if ( StringUtils.isNotEmpty( lang ) ) {
			bundle = bundleMap.get( lang );
		}
		return bundle;
	}
	
	public String getString( String lang, String key, Object... params ) {
		ResourceBundle bundle = findBundle( lang );
		String msg = null;
		if ( bundle != null ) {
			msg = bundle.getString( key );
			if ( msg != null && params != null && params.length > 0 ) {
				Object[] realParams = new Object[ params.length ];
				for ( int k=0; k<params.length; k++ ) {
					Object current = params[k];
					if ( current instanceof ParamI18N )  {
						current = bundle.getString( ((ParamI18N)current).getKey() );			
					}
				}
				msg = MessageFormat.format( msg, realParams );
			}
		}
		return msg;
	}
	
}
