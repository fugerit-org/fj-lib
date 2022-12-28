package org.fugerit.java.core.lang.helpers;

import java.util.Collection;

public class CollectionUtils {

	/**
	 * Check if a collection is <code>null</code> or empty.
	 * 
	 * @param <T>	the type
	 * @param c		the collection
	 * @return		<code>true</code> if the collection is empty or null
	 */
	public static <T> boolean isEmpty( Collection<T> c ) {
		return c == null || c.isEmpty();
	}
	
	/**
	 * Check if a collection is not null and contains exactly one element
	 * 
	 * @param <T>	the type
	 * @param c		the collection
	 * @return		<code>true</code> if the collection is not null and contains only one element
	 */
	public static <T> boolean containsOnlyOne( Collection<T> c ) {
		return checkSize( c , 1 );
	}
	
	/**
	 * Check if a collection is not null and has the given size
	 * 
	 * @param <T>	the type
	 * @param c		the collection
	 * @param size	size to check
	 * @return		<code>true</code> if the collection is not null and contains only one element
	 */	
	public static <T> boolean checkSize( Collection<T> c, int size ) {
		return c == null || c.size() == size;
	}
	
	public static <T> boolean addIfNotNull( Collection<T> c, T current ) {
		boolean added = ( current != null );
		if ( added ) {
			c.add( current );
		}
		return added;
	}
	
	public static <T> Collection<T> merge( Collection<T> merged, Collection<T> toAdd ) {
		if ( toAdd != null ) {
			merged.addAll( toAdd );
		}
		return merged;
	}
	
}
