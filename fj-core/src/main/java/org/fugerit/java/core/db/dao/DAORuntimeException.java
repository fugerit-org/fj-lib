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

import org.fugerit.java.core.lang.ex.ExConverUtils;

/*
*
* @author Fugerit
*
*/
public class DAORuntimeException extends RuntimeException {

	/*
	 * 
	 */
	private static final long serialVersionUID = -84599783950196700L;


	public DAORuntimeException() {
		super();
	}

	public DAORuntimeException(String message) {
		super(message);
	}

	public DAORuntimeException(String message, Throwable t) {
		super(message, t);
	}
	
	public DAORuntimeException(Throwable t) {
		super(t);
	}
	
	public static DAORuntimeException convertEx( String baseMessage, Exception e ) {
		DAORuntimeException res = null;
		if ( e instanceof DAORuntimeException ) {
			res = (DAORuntimeException)e;
		} else {
			res = new DAORuntimeException( ExConverUtils.defaultMessage(baseMessage, e), e );
		}
		return res;
	}

	public static DAORuntimeException convertExMethod( String method, Exception e ) {
		return convertEx( ExConverUtils.defaultMethodMessage(method), e );
	}
	
	public static DAORuntimeException convertEx( Exception e ) {
		return convertEx( ExConverUtils.DEFAULT_CAUSE_MESSAGE, e );
	}
	
}
