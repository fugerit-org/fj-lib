package org.fugerit.java.core.io.helper;

import java.io.IOException;

import org.fugerit.java.core.lang.ex.ExConverUtils;

public class HelperIOException {

	private HelperIOException() {}
	
	public static IOException convertEx( String baseMessage, Exception e ) {
		IOException res = null;
		if ( e instanceof IOException ) {
			res = (IOException)e;
		} else {
			res = new IOException( ExConverUtils.defaultMessage(baseMessage, e), e );
		}
		return res;
	}
	
	public static IOException convertExMethod( String method, Exception e ) {
		return convertEx( ExConverUtils.defaultMethodMessage(method), e );
	}
	
	public static IOException convertEx( Exception e ) {
		return convertEx( ExConverUtils.DEFAULT_CAUSE_MESSAGE, e );
	}
	
}