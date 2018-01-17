package org.fugerit.java.core.lang.helpers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocaleHelper {

	public static final boolean USE_DEFAULT_ON_ERROR = true;
	
	private static final Logger logger = LoggerFactory.getLogger(LocaleHelper.class);
	
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
				logger.info( "New locale set : "+loc );
			} catch (Exception e) {
				logger.warn( "Failed to configure locale, using default : "+loc+" : "+e );
			}
		}
		return loc;
	}
	
}
