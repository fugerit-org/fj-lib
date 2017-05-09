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
