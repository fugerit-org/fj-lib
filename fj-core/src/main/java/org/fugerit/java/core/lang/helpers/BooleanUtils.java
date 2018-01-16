package org.fugerit.java.core.lang.helpers;

public class BooleanUtils {

	public static final String BOOLEAN_TRUE = "true";
	public static final String BOOLEAN_1 = "1";
	
	public static boolean isTrue( String s ) {
		return BOOLEAN_TRUE.equalsIgnoreCase( s ) || BOOLEAN_1.equals( s );
	}
	
}
