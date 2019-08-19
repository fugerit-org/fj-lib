package org.fugerit.java.core.db.daogen;

import java.io.Serializable;

public class BasicHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7545568256714803873L;

	protected static final boolean UNSOPPORTED_OPERATION = true;
	
	public static void throwUnsupported( String message ) {
		if ( UNSOPPORTED_OPERATION ) {
			throw new UnsupportedOperationException( message );
		}
	}
	
}
