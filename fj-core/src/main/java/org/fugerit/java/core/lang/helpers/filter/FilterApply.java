package org.fugerit.java.core.lang.helpers.filter;

public interface FilterApply {

	boolean accept( Object target, FilterInfo filter ) throws Exception;
	
}
