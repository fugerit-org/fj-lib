package org.fugerit.java.core.lang.compare;

import org.fugerit.java.core.lang.helpers.StringUtils;

public class CheckEmptyHelper {

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
