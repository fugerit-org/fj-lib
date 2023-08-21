package org.fugerit.java.core.db.dao;

import java.io.Closeable;

public class CloseDAOHelper {

	private CloseDAOHelper() {}
	
	private static final String BASIC_CLOSE_MESSAGE = "Error closing object : ";
	
	public static void close( AutoCloseable ac ) throws DAOException {
		if ( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				throw DAOException.convertEx( BASIC_CLOSE_MESSAGE+ac , e );
			}
		}
	}
	
	public static void close( Closeable ac ) throws DAOException {
		if ( ac != null ) {
			try {
				ac.close();
			} catch (Exception e) {
				throw DAOException.convertEx( BASIC_CLOSE_MESSAGE+ac , e );
			}
		}
	}
	
}
