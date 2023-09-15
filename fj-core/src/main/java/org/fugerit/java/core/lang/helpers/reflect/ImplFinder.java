package org.fugerit.java.core.lang.helpers.reflect;

public interface ImplFinder {

	Class<?> findImplFor( Class<?> c );
	
	boolean isFinderFor( Class<?> c );
	
}
