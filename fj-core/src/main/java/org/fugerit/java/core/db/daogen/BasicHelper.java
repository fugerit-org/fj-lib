package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
import java.util.function.BooleanSupplier;

public class BasicHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7545568256714803873L;

	private static final boolean UNSUPPORTED_OPERATION = true;

	public static void throwUnsupported( String message ) {
		throwUnsupported( message, () -> UNSUPPORTED_OPERATION );
	}

	public static void throwUnsupported(String message, BooleanSupplier isUnsupported) {
		if ( isUnsupported.getAsBoolean() ) {
			throw new UnsupportedOperationException( message );
		}
	}
	
}
