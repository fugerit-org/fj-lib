package org.fugerit.java.core.lang.helpers.reflect;

import java.lang.reflect.Field;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

/**
 * <p>Helper to set / get field in objects.</p>
 * 
 * <p>Note : use with extreme caution.</p>
 * 
 * @see <a href="https://sonarcloud.io/organizations/fugerit-org/rules?open=java%3AS3011&rule_key=java%3AS3011">Reflection should not be used to increase accessibility of classes, methods, or fields</a>
 * 
 */
public class FieldHelper {

	private FieldHelper() {}
	
	public static final boolean DEFAULT_SET_ACCESSIBLE_FALSE = Boolean.FALSE.booleanValue();
	
	/**
	 * <p>Set a field on an object.</p>
	 * 
	 * @param target			the target object
	 * @param fieldName			the field name
	 * @param value				the value
	 * @param setAccessible		if the field accessibility should be overridden (use with caution, see below)
	 * 
	 * @see <a href="https://sonarcloud.io/organizations/fugerit-org/rules?open=java%3AS3011&rule_key=java%3AS3011">Reflection should not be used to increase accessibility of classes, methods, or fields</a>
	 */
	public static void setField( Object target, String fieldName, Object value, boolean setAccessible ) {
		try {
			Class<?> c = target.getClass();
			Field field = c.getDeclaredField( fieldName );
			if ( setAccessible ) {
				field.setAccessible( Boolean.TRUE.booleanValue() );
			}
			field.set( target , value );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "setField", e );
		}
	}
	
	/**
	 * <p>Set a field on an object.</p>
	 * 
	 * @param target			the target object
	 * @param fieldName			the field name
	 * @param value				the value
	 */
	public static void setField( Object target, String fieldName, Object value ) {
		setField(target, fieldName, value, DEFAULT_SET_ACCESSIBLE_FALSE);
	}
	
	/**
	 *  <p>Get a field on an object.</p>
	 * 
	 * @param target			the target object
	 * @param fieldName			the field name
	 * @param setAccessible		if the field accessibility should be overridden (use with caution, see below)
	 * @return					the value of the field
	 * 
	 * @see <a href="https://sonarcloud.io/organizations/fugerit-org/rules?open=java%3AS3011&rule_key=java%3AS3011">Reflection should not be used to increase accessibility of classes, methods, or fields</a>
	 */
	public static Object getField( Object target, String fieldName, boolean setAccessible ) {
		Object res = null;
		try {
			Class<?> c = target.getClass();
			Field field = c.getDeclaredField( fieldName );
			if ( setAccessible ) {
				field.setAccessible( Boolean.TRUE.booleanValue() );
			}
			res = field.get( target );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "getField", e );
		}
		return res;
	}
	
	/**
	 *  <p>Get a field on an object.</p>
	 * 
	 * @param target			the target object
	 * @param fieldName			the field name
	 * @return					the value of the field
	 */
	public static Object getField( Object target, String fieldName ) {
		return getField(target, fieldName, DEFAULT_SET_ACCESSIBLE_FALSE);
	}
	
}
