package org.fugerit.java.core.lang.helpers.reflect;

import java.lang.reflect.Field;

public class FieldHelper {

	public static void setField( Object target, String fieldName, Object value ) throws Exception {
		Class<?> c = target.getClass();
		Field field = c.getDeclaredField( fieldName );
		field.set( target , value );
	}
	
	public static Object getField( Object target, String fieldName ) throws Exception {
		Class<?> c = target.getClass();
		Field field = c.getDeclaredField( fieldName );
		return field.get( target );
	}
	
}
