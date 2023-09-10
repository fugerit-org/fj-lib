package org.fugerit.java.core.db.dao;

import java.io.Closeable;

public class CloseDAOHelper {

	private CloseDAOHelper() {}
	
	public static void close( AutoCloseable ac ) throws DAOException {
		DAOException.apply( () -> { 
			if ( ac != null ) 
				ac.close(); 
		} );
	}
	
	public static void close( Closeable ac ) throws DAOException {
		DAOException.apply( () -> { 
			if ( ac != null ) 
				ac.close(); 
		} );
	}
	
}
