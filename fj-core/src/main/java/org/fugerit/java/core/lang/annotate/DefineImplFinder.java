package org.fugerit.java.core.lang.annotate;

import java.io.Serializable;

import org.fugerit.java.core.lang.helpers.reflect.ImplFinder;

public class DefineImplFinder implements ImplFinder, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -758765146259093086L;
	
	public static final ImplFinder DEFAULT = new DefineImplFinder();
	
	@Override
	public Class<?> findImplFor(Class<?> c) {
		Class<?> ret = c;
		if ( this.isFinderFor( c )) {
			DefineImpl fug = c.getAnnotation( DefineImpl.class );
			ret = fug.as();
	    }
		return ret;
	}

	@Override
	public boolean isFinderFor(Class<?> c) {
		return c.isAnnotationPresent( DefineImpl.class );
	}

}
