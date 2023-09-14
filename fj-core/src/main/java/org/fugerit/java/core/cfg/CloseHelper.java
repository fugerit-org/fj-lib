package org.fugerit.java.core.cfg;

import java.io.Closeable;

import org.fugerit.java.core.function.SafeFunction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseHelper {

	private CloseHelper() {}
	
	private static final String BASIC_CLOSE_MESSAGE = "Error closing object : ";
	
	private static final String BASIC_CLOSE_MESSAGE_SILENT = "[just tracing silenctly]"+BASIC_CLOSE_MESSAGE;
	
	public static void close( AutoCloseable ac ) throws ConfigException {
		if ( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				throw ConfigException.convertEx( BASIC_CLOSE_MESSAGE+ac , e );
			}
		}
	}
	
	public static void close( Closeable ac ) throws ConfigException {
		if ( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				throw ConfigException.convertEx( BASIC_CLOSE_MESSAGE+ac , e );
			}
		}
	}
	
	public static void closeRuntimeEx( AutoCloseable ac ) {
		if ( ac != null ) {
			SafeFunction.apply( ac::close, e -> { throw ConfigRuntimeException.convertEx( BASIC_CLOSE_MESSAGE+ac , e ); } );
		}
	}
	
	public static void closeRuntimeEx( Closeable ac ) {
		if ( ac != null ) {
			SafeFunction.apply( ac::close, e -> { throw ConfigRuntimeException.convertEx( BASIC_CLOSE_MESSAGE+ac , e ); } );
		}
	}
	
	public static boolean closeSilent( AutoCloseable ac ) {
		return SafeFunction.getWithDefault( 
				() -> {
					if ( ac != null ) {
						ac.close();
					}
					return ac != null;
				}, 
				e -> { 
					log.warn( BASIC_CLOSE_MESSAGE_SILENT+ac , e ); 
					return Boolean.FALSE; 
				} );
	}
	
	public static boolean closeSilent( Closeable ac ) {
		return SafeFunction.getWithDefault( 
				() -> {
					if ( ac != null ) {
						ac.close();
					}
					return ac != null;
				}, 
				e -> { 
					log.warn( BASIC_CLOSE_MESSAGE_SILENT+ac , e ); 
					return Boolean.FALSE; 
				} );
	}

	private static Exception handle( Object obj, Exception e ) {
		log.warn( "Exception closing : "+obj, e );
		return e;
	}
	
	public static Exception closeAll( Closeable... c ) {
		Exception res = null;
		for ( Closeable current : c ) {
			try {
				if ( current != null ) {
					current.close();
				}
			} catch (Exception e) {
				res = handle(c, e);
			}
		}
		return res;
	}
	
	public static Exception closeAll( AutoCloseable... c ) {
		Exception res = null;
		for ( AutoCloseable current : c ) {
			try {
				if ( current != null ) {
					current.close();
				}
			} catch (Exception e) {
				res = handle(c, e);
			}
		}
		return res;
	}
	
	public static void closeAllAndThrowConfigRuntime( AutoCloseable... c ) {
		Exception res = closeAll( c );
		if ( res != null ) {
			throw new ConfigRuntimeException( res );
		}
	}
	
}
