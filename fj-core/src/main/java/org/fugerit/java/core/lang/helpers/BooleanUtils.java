package org.fugerit.java.core.lang.helpers;

/**
 * Helper class for boolean check.
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BooleanUtils {

	private BooleanUtils() {}
	
	/**
	 * Constant for "true" String
	 */
	public static final String BOOLEAN_TRUE = "true";
	
	/**
	 * Constant for "1" String
	 */
	public static final String BOOLEAN_1 = "1";
	
	/**
	 * Constant for "false" String
	 */
	public static final String BOOLEAN_FALSE = "false";
	
	/**
	 * Constant for "0" String
	 */
	public static final String BOOLEAN_0 = "0";
	
	/**
	 * Check if String is <code>true</code>
	 * 
	 * NOTE: Only "1" and "true" are considered to be a <code>boolean true</code>, any other value return <code>boolean false</code>.
	 * 
	 * @param s	the String to check
	 * @return	<code>true</code> if the input String is "1" or "true", <code>false</code> otherwise.
	 */
	public static boolean isTrue( String s ) {
		return BOOLEAN_TRUE.equalsIgnoreCase( s ) || BOOLEAN_1.equals( s );
	}
	
}
