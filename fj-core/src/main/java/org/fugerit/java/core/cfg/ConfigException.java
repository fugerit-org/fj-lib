/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.cfg;

import org.fugerit.java.core.function.UnsafeConsumer;
import org.fugerit.java.core.function.UnsafeSupplier;
import org.fugerit.java.core.function.UnsafeVoid;
import org.fugerit.java.core.lang.ex.CodeException;
import org.fugerit.java.core.lang.ex.ExConverUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Exception for handling unexpected situations during configuration.</p>
 *
 * <p>Config exception is able to wrap a code.</p>
 *
 * @author Fugerit
 *
 */
@Slf4j
public class ConfigException extends CodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6673695755101705239L;
	
	/**
	 * <p>Default value for the code field in a ConfigException.</p>
	 */
	public static final int DEFAULT_CODE = CodeException.DEFAULT_CODE;

	public ConfigException() {
		super();
	}

	public ConfigException(int code) {
		super(code);
	}

	public ConfigException(String message, int code) {
		super(message, code);
	}

	public ConfigException(String message, Throwable cause, int code) {
		super(message, cause, code);
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause, int code) {
		super(cause, code);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

	public static ConfigException stadardExceptionWrapping( Exception e ) throws ConfigException {
		throw convertEx( "Configuration error", e );
	}
	
	public static ConfigException convertEx( String baseMessage, Exception e ) {
		ConfigException res = null;
		if ( e instanceof ConfigException ) {
			res = (ConfigException)e;
		} else {
			res = new ConfigException( ExConverUtils.defaultMessage(baseMessage, e), e );
		}
		return res;
	}

	public static ConfigException convertExMethod( String method, Exception e ) {
		return convertEx( ExConverUtils.defaultMethodMessage(method), e );
	}
	
	public static ConfigException convertEx( Exception e ) {
		return convertEx( ExConverUtils.DEFAULT_CAUSE_MESSAGE, e );
	}
	
	public static final UnsafeConsumer<Exception, ConfigException> EX_HANDLER_SILENT = e -> log.warn( "Suppressed exception : "+e, e );
	
	public static final UnsafeConsumer<Exception, ConfigException> EX_HANDLER_RETHROW = e -> { throw convertEx( e ); };
	
	public static final UnsafeConsumer<Exception, ConfigException> EX_HANDLER_DEFAULT = EX_HANDLER_RETHROW;
	
	private static UnsafeConsumer<Exception, ConfigException> createRethrowWithMessageExHandler( String message ) {
		return e -> { throw convertEx( message , e); };
	}
	
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> fun ) throws ConfigException {
		return get( fun, EX_HANDLER_DEFAULT );
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun ) throws ConfigException {
		apply(fun, EX_HANDLER_DEFAULT);
	}
	
	public static <T, E extends Exception> T getSilent( UnsafeSupplier<T, E> fun ) throws ConfigException {
		return get( fun, EX_HANDLER_SILENT );
	}
	
	public static <E extends Exception> void applySilent( UnsafeVoid<E> fun ) throws ConfigException {
		apply(fun, EX_HANDLER_SILENT);
	}
		
	public static <T, E extends Exception> T getWithMessage( UnsafeSupplier<T, E> fun, String message ) throws ConfigException {
		return get( fun, createRethrowWithMessageExHandler( message ) );
	}
	
	public static <E extends Exception> void applyWithMessage( UnsafeVoid<E> fun, String message ) throws ConfigException {
		apply( fun,  createRethrowWithMessageExHandler( message ) );
	}
	
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> fun, UnsafeConsumer<Exception, ConfigException> exHandler ) throws ConfigException {
		T res = null;
		try {
			res = fun.get();
		} catch (Exception e) {
			exHandler.accept( e );
		}
		return res;
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun, UnsafeConsumer<Exception, ConfigException> exHandler ) throws ConfigException {
		try {
			fun.apply();
		} catch (Exception e) {
			exHandler.accept( e );
		}
	}
	
}
