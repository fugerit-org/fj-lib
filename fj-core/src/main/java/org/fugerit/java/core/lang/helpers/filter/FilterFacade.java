package org.fugerit.java.core.lang.helpers.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class FilterFacade {

	private FilterFacade() {}
	
	public static final FilterApply DEFAULT_APPLY = new FilterApplyDefault();
	
	public static <T extends FilterInfo> boolean  accept( Object target, Collection<T> filters ) throws Exception {
		return accept(target, DEFAULT_APPLY, filters);
	}
	
	public static <T extends FilterInfo> boolean  accept( Object target, FilterApply apply, Collection<T> filters ) throws Exception {
		boolean accept = true;
		Iterator<T> it = filters.iterator();
		while ( accept && it.hasNext() ) {
			accept = apply.accept( target , it.next() );
		}
		return accept;
	}
	
	public static <T extends FilterInfo> boolean  accept( Object target, T filter ) throws Exception {
		return accept(target, DEFAULT_APPLY, filter);
	}
	
	public static <T extends FilterInfo> boolean  accept( Object target, FilterApply apply, T filter ) throws Exception {
		Collection<T> filters = new ArrayList<>();
		filters.add( filter );
		return accept(target, apply, filters);
	}
	
}
