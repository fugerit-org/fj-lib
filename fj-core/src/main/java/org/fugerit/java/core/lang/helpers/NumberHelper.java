package org.fugerit.java.core.lang.helpers;

public class NumberHelper {

	public static boolean isNull( Number n ) {
		return n == null;
	}
	
	public static boolean isNullOrZero( Number n ) {
		return n == null || n.longValue() == 0;
	}
	
}
