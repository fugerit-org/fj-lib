package org.fugerit.java.core.lang.ex;

public class ExConverUtils {

	private ExConverUtils() {}
	
	public static final String DEFAULT_CAUSE_MESSAGE = "DocException cause is";
	
	public static String defaultMethodMessage( String method ) {
		return "Exception during "+method;
	}

	public static String defaultMessage( String baseMessage, Exception e ) {
		return baseMessage+" : "+e;
	}
	
}
