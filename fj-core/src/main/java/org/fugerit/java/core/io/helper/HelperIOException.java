package org.fugerit.java.core.io.helper;

import java.io.IOException;

public class HelperIOException {

	private HelperIOException() {}
	
	public static IOException convertExMethod( String method, Exception e ) {
		return convertEx( "Exception during "+method, e );
	}
	
	public static IOException convertEx( String baseMessage, Exception e ) {
		IOException res = null;
		if ( e instanceof IOException ) {
			res = (IOException)e;
		} else {
			res = new IOException( baseMessage+" : "+e, e );
		}
		return res;
	}
	
	public static IOException convertEx( Exception e ) {
		return convertEx( "IOException cause is", e );
	}
	
}
