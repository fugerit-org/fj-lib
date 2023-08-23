package org.fugerit.java.core.lang.helpers.reflect;

import java.lang.reflect.Field;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

public class FieldHelper {

	private FieldHelper() {}
	
	public static void setField( Object target, String fieldName, Object value ) {
		try {
			Class<?> c = target.getClass();
			Field field = c.getDeclaredField( fieldName );
			field.set( target , value );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "setField", e );
		}
	}
	
	public static Object getField( Object target, String fieldName ) {
		Object res = null;
		try {
			Class<?> c = target.getClass();
			Field field = c.getDeclaredField( fieldName );
			res = field.get( target );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "getField", e );
		}
		return res;
	}
	
}
