package org.fugerit.java.core.lang.helpers.reflect;

public interface ImplFinder {

	Class<?> findImplFor( Class<?> c ) throws Exception;
	
	boolean isFinderFor( Class<?> c ) throws Exception;
	
}
