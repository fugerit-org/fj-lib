package org.fugerit.java.core.cfg;

import java.io.Closeable;

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
			try {
				ac.close();
			} catch (Exception e) {
				throw ConfigRuntimeException.convertEx( BASIC_CLOSE_MESSAGE+ac , e );
			}
		}
	}
	
	public static void closeRuntimeEx( Closeable ac ) {
		if ( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				throw ConfigRuntimeException.convertEx( BASIC_CLOSE_MESSAGE+ac , e );
			}
		}
	}
	
	public static boolean closeSilent( AutoCloseable ac ) {
		boolean closed = true;
		if ( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				closed = false;
				log.warn( BASIC_CLOSE_MESSAGE_SILENT+ac , e );
			}
		}
		return closed;
	}
	
	public static boolean closeSilent( Closeable ac ) {
		boolean closed = true;
		if ( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				closed = false;
				log.warn( BASIC_CLOSE_MESSAGE_SILENT+ac , e );
			}
		}
		return closed;
	}
	
}
