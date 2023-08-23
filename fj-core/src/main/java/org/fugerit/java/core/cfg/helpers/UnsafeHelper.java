package org.fugerit.java.core.cfg.helpers;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnsafeHelper {

	private UnsafeHelper() {}
	
	private final static Logger logger = LoggerFactory.getLogger( UnsafeHelper.class );
	
	public static final String UNSAFE_TRUE = BooleanUtils.BOOLEAN_TRUE;
	public static final String UNSAFE_FALSE = BooleanUtils.BOOLEAN_FALSE;
	
	public static final String UNSAFE_MODE_LOG_TRACE = "log-trace";
	public static final String UNSAFE_MODE_LOG_MESSAGE = "log-message";
	public static final String UNSAFE_MODE_DEFAULT = UNSAFE_MODE_LOG_MESSAGE;
	
	public static void handleUnsafe( Exception e, String unsafe ) throws ConfigException {
		handleUnsafe(e, unsafe, null);
	}
	
	public static void handleUnsafe( Exception e, String unsafe, String unsafeMode ) throws ConfigException {
		handleUnsafe(logger, e, unsafe, unsafeMode);
	}
	
	public static void handleUnsafe( Logger l, Exception e, boolean unsafeHandle, String unsafeMode ) throws ConfigException {
		unsafeMode = StringUtils.valueWithDefault( unsafeMode , UNSAFE_MODE_DEFAULT );
		String message = "Error handling unsafe section "+e;
		if ( unsafeHandle ) {
			if ( UNSAFE_MODE_LOG_TRACE.equalsIgnoreCase( unsafeMode ) ) {
				l.warn( message, e );
			} else {
				l.warn( "{} [trace suppressed, set unsafe-mode='log-trace' to show]", message );
			}
		} else {
			ConfigException cf = null;
			if ( e instanceof ConfigException ) {
				cf = (ConfigException)e;
			} else {
				cf = new ConfigException( message, e );
			}
			throw cf;
		}
	}
	
	public static void handleUnsafe( Logger l, Exception e, String unsafe, String unsafeMode ) throws ConfigException {
		boolean unsafeHandle = BooleanUtils.isTrue( unsafe );
		handleUnsafe( l, e, unsafeHandle, unsafeMode );
	}
	
}
