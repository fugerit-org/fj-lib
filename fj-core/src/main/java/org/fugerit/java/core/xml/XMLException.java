package org.fugerit.java.core.xml;

import java.util.function.Function;

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
    

}
