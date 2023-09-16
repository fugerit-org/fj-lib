package org.fugerit.java.core.xml;

import java.util.function.Function;

import org.fugerit.java.core.function.UnsafeSupplier;
import org.fugerit.java.core.function.UnsafeVoid;

/*
 * 
 *
 * @author Morozko
 *
 */
public class XMLException extends Exception {

	/*
	 * 
	 */
	private static final long serialVersionUID = -5111578010834483982L;

    public XMLException() {
        super();
    }

    public XMLException(String message) {
        super(message);
    }

    public XMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLException(Throwable cause) {
        super(cause);
    }

    public static final Function<Exception, XMLException> CONVERT_FUN = e -> {
    	XMLException res = null;
    	if ( e != null ) {
    		res = new XMLException( e );
    	}
    	return res;
    };
    
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> fun ) throws XMLException {
		T res = null;
		try {
			res = fun.get();
		} catch (Exception e) {
			throw CONVERT_FUN.apply( e );
		}
		return res;
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun ) throws XMLException {
		try {
			fun.apply();
		} catch (Exception e) {
			throw CONVERT_FUN.apply( e );
		}
	}
    
}
