package org.fugerit.java.core.lang.helpers;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocaleHelper {

	private LocaleHelper() {}
	
	public static final boolean USE_DEFAULT_ON_ERROR = true;
	
	public static Locale convertLocale( String locale, boolean useDefaultOnError ) {
		Locale loc = null;
		if ( useDefaultOnError ) {
			loc = Locale.getDefault();
			try {
				String[] split = locale.split( "_" );
				if ( split.length == 1 ) {
					loc = new Locale( split[0] );
				} else if ( split.length == 2 ) {
					loc = new Locale( split[0], split[1] );
				}
				log.info( "New locale set : {}", loc );
			} catch (Exception e) {
				log.warn( "Failed to configure locale, using default : {}, exception : {}", e, loc != null ? loc.toString() : null );
			}
		}
		return loc;
	}
	
}
