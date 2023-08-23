package org.fugerit.java.core.util.i18n;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.fugerit.java.core.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HelperI18N {
		
	protected HelperI18N( String baseName, String defaultLanguge,  String... altLocale ) {
		log.trace( "baseName:{}, defaultLanguage:{}", baseName, defaultLanguge );
		this.defaultLanguage = defaultLanguge;
		this.altLocale = altLocale;
		if ( this.altLocale == null ) {
			this.altLocale = new String[0];
		}
	}

	private String defaultLanguage;
	
	private String[] altLocale;
	
	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public List<String> getAltLocale() {
		return Arrays.asList( this.altLocale );
	}

	public abstract String resolveString( String lang, String key );
	
	public String getString( String lang, String key, Object... params ) {
		String message = this.resolveString(lang, key);
		if ( message != null && params != null && params.length > 0 ) {
			Object[] realParams = new Object[ params.length ];
			for ( int k=0; k<params.length; k++ ) {
				Object current = params[k];
				if ( current instanceof ParamI18N )  {
					ParamI18N param = (ParamI18N)current;
					HelperI18N helper = ObjectUtils.objectWithDefault( param.getHelper() , this );
					current = helper.resolveString( lang, param.getKey() );			
				}
				realParams[k] = current;
			}
			message = MessageFormat.format( message, realParams );
		}
		return message;
	}
	
}
