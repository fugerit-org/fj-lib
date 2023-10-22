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
package org.fugerit.java.core.db.dao;

import org.fugerit.java.core.function.UnsafeConsumer;
import org.fugerit.java.core.function.UnsafeSupplier;
import org.fugerit.java.core.function.UnsafeVoid;
import org.fugerit.java.core.lang.ex.ExConverUtils;

import lombok.extern.slf4j.Slf4j;

/*
*
* @author Fugerit
*
*/
@Slf4j
public class DAOException extends Exception {

	/*
	 * 
	 */
	private static final long serialVersionUID = -8459978395011496700L;


	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable t) {
		super(message, t);
	}
	
	public DAOException(Throwable t) {
		super(t);
	}
	
	public static DAOException convertEx( String baseMessage, Exception e ) {
		DAOException res = null;
		if ( e instanceof DAOException ) {
			res = (DAOException)e;
		} else {
			res = new DAOException( ExConverUtils.defaultMessage(baseMessage, e), e );
		}
		return res;
	}

	public static DAOException convertExMethod( String method, Exception e ) {
		return convertEx( ExConverUtils.defaultMethodMessage(method), e );
	}
	
	public static DAOException convertEx( Exception e ) {
		return convertEx( ExConverUtils.DEFAULT_CAUSE_MESSAGE, e );
	}

	public static final UnsafeConsumer<Exception, DAOException> EX_HANDLER_SILENT = e -> log.warn( "Suppressed exception : "+e, e );
	
	public static final UnsafeConsumer<Exception, DAOException> EX_HANDLER_RETHROW = e -> { throw convertEx( e ); };
	
	public static final UnsafeConsumer<Exception, DAOException> EX_HANDLER_DEFAULT = EX_HANDLER_RETHROW;
	
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> fun ) throws DAOException {
		return get( fun, EX_HANDLER_DEFAULT );
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun ) throws DAOException {
		apply(fun, EX_HANDLER_DEFAULT);
	}
	
	public static <T, E extends Exception> T getSilent( UnsafeSupplier<T, E> fun ) throws DAOException {
		return get( fun, EX_HANDLER_SILENT );
	}
	
	public static <E extends Exception> void applySilent( UnsafeVoid<E> fun ) throws DAOException {
		apply(fun, EX_HANDLER_SILENT);
	}
		
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> fun, UnsafeConsumer<Exception, DAOException> exHandler ) throws DAOException {
		T res = null;
		try {
			res = fun.get();
		} catch (Exception e) {
			exHandler.accept( e );
		}
		return res;
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun, UnsafeConsumer<Exception, DAOException> exHandler ) throws DAOException {
		try {
			fun.apply();
		} catch (Exception e) {
			exHandler.accept( e );
		}
	}
	
}
