package org.fugerit.java.core.lang.annotate;

public class FacadeFugeritImpl {

	public static Class<?> resolveImplClass( Class<?> propertyClass ) {
		Class<?> ret = propertyClass;
		if ( propertyClass.isAnnotationPresent(FugeritImpl.class) ) {
			FugeritImpl fug = propertyClass.getAnnotation( FugeritImpl.class );
			ret = fug.as();
	    }
		return ret;
	}
	
}
