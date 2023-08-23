package org.fugerit.java.core.lang.annotate;

public class FacadeDefineImpl {

	private FacadeDefineImpl() {}
	
	public static Class<?> resolveImplClass( Class<?> propertyClass ) {
		Class<?> ret = propertyClass;
		if ( propertyClass.isAnnotationPresent(DefineImpl.class) ) {
			DefineImpl fug = propertyClass.getAnnotation( DefineImpl.class );
			ret = fug.as();
	    }
		return ret;
	}
	
}
