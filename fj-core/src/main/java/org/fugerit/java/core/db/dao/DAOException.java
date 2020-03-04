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

}
