package org.fugerit.java.core.io.helper;

import java.io.Closeable;
import java.io.IOException;

import org.fugerit.java.core.function.UnsafeConsumer;
import org.fugerit.java.core.function.UnsafeSupplier;
import org.fugerit.java.core.function.UnsafeVoid;
import org.fugerit.java.core.lang.ex.ExConverUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelperIOException extends IOException {

	private static final long serialVersionUID = -74952101536562005L;

	public HelperIOException() {
		super();
	}

	public HelperIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public HelperIOException(String message) {
		super(message);
	}

	public HelperIOException(Throwable cause) {
		super(cause);
	}

	public static IOException convertEx( String baseMessage, Exception e ) {
		IOException res = null;
		if ( e instanceof IOException ) {
			res = (IOException)e;
		} else {
			res = new IOException( ExConverUtils.defaultMessage(baseMessage, e), e );
		}
		return res;
	}
	
	public static IOException convertExMethod( String method, Exception e ) {
		return convertEx( ExConverUtils.defaultMethodMessage(method), e );
	}
	
	public static IOException convertEx( Exception e ) {
		return convertEx( ExConverUtils.DEFAULT_CAUSE_MESSAGE, e );
	}
	
	public static void close( AutoCloseable ac ) throws IOException {
		apply( () -> { if ( ac != null ) ac.close(); } );
	}
	
	public static void close( Closeable ac ) throws IOException {
		apply( () -> { if ( ac != null ) ac.close(); } );
	}
	
	public static final UnsafeConsumer<Exception, IOException> EX_HANDLER_SILENT = e -> log.warn( "Suppressed exception : "+e, e );
	
	public static final UnsafeConsumer<Exception, IOException> EX_HANDLER_RETHROW = e -> { throw convertEx( e ); };
	
	public static final UnsafeConsumer<Exception, IOException> EX_HANDLER_DEFAULT = EX_HANDLER_RETHROW;
	
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> fun ) throws IOException {
		return get( fun, EX_HANDLER_DEFAULT );
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun ) throws IOException {
		apply(fun, EX_HANDLER_DEFAULT);
	}
	
	public static <T, E extends Exception> T getSilent( UnsafeSupplier<T, E> fun ) throws IOException {
		return get( fun, EX_HANDLER_SILENT );
	}
	
	public static <E extends Exception> void applySilent( UnsafeVoid<E> fun ) throws IOException {
		apply(fun, EX_HANDLER_SILENT);
	}
		
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> fun, UnsafeConsumer<Exception, IOException> exHandler ) throws IOException {
		T res = null;
		try {
			res = fun.get();
		} catch (Exception e) {
			exHandler.accept( e );
		}
		return res;
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun, UnsafeConsumer<Exception, IOException> exHandler ) throws IOException {
		try {
			fun.apply();
		} catch (Exception e) {
			exHandler.accept( e );
		}
	}
	
}
