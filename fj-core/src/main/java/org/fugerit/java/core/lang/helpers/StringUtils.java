package org.fugerit.java.core.lang.helpers;

public class StringUtils {

	public static boolean isEmpty( String s ) {
		return s == null || s.trim().length() == 0;
	}
	
	public static boolean isNotEmpty( String s ) {
		return !isEmpty( s );
	}
	
}
