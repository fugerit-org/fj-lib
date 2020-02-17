package org.fugerit.java.core.lang.compare;

import org.fugerit.java.core.lang.helpers.StringUtils;

public class CheckEmptyHelper {

	/**
	 * Check if a object is empty.
	 * 
	 * If the object is null then is empty
	 * If the object is a string then even empty string is
	 * If the object implements CheckEmpty interface that method is checked
	 * 
	 * @param v		the item to be checked
	 * @return		true if the item is null or logically empty
	 */
	public static boolean isEmpty( Object v ) {
		boolean empty = ( v == null );
		if ( !empty ) {
			if( v instanceof String ) {
				empty = StringUtils.isEmpty( (String)v );
			} else if ( v instanceof CheckEmpty ) {
				empty = ((CheckEmpty)v).isEmpty();
			}
		}
		return empty;
	}
	
}
