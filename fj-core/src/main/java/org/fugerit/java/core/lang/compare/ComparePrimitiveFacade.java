/*
 *		Fugerit Java Library is distributed under the terms of :
 *
 *                                Apache License
 *                          Version 2.0, January 2004
 *                       http://www.apache.org/licenses/
 *
 *
 *	Full license :
 *		http://www.apache.org/licenses/LICENSE-2.0
 *		
 *	Project site: 
 *		https://www.fugerit.org/
 *	
 *	SCM site :
 *		https://github.com/fugerit79/fj-lib	
 *
 */
package org.fugerit.java.core.lang.compare;

/**
 * <p>This class provides utilities for comparing primitive types.</p>
 * 
 * @author Fugerit
 *
 */
public class ComparePrimitiveFacade {

	/**
	 * <p>Check if a target value is among one or more values.</p>
	 * 
	 * <p>NOTE: if target is <code>null</code> returns always <code>false</code>.</p>
	 * 
	 * @param target	the target value
	 * @param value		List of values to check
	 * @return			<code>true</code> if target is among the values, <code>false</code> otherwise.
	 */
	public static boolean compareInt( Integer target, Integer... value ) {
		boolean result = false;
		if ( target != null ) {
			for (int k = 0; k < value.length && !result; k++) {
				result = target.equals( value[k] );
			}
		}
		return result;
	}
	
}
