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
	
}
