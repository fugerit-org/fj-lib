package org.fugerit.java.core.lang.helpers;

import java.util.Collection;

public class CollectionUtils {

	public static <T> boolean addIfNotNull( Collection<T> c, T current ) {
		boolean added = ( current != null );
		if ( added ) {
			c.add( current );
		}
		return added;
	}
	
}
