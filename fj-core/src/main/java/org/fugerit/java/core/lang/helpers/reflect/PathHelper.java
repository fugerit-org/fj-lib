package org.fugerit.java.core.lang.helpers.reflect;

import java.beans.PropertyDescriptor;

import org.fugerit.java.core.lang.annotate.FacadeFugeritImpl;

/**
 * Simple class to look up a property path in a java object
 * 
 * @author fugerit
 *
 */
public class PathHelper {

	public static final boolean CONTINUE_ON_NULL = true;
	
	public static final boolean EXIT_ON_NULL = false;
	
	public static Object initEmptyField( String property, Object target ) throws Exception {
		PropertyDescriptor pd = new PropertyDescriptor( property, target.getClass() );
		Class<?> propertyClass = pd.getReadMethod().getReturnType();
		Class<?> implClass = FacadeFugeritImpl.resolveImplClass( propertyClass );
		Object temp = implClass.newInstance();
		pd.getWriteMethod().invoke(target, temp);
		return temp;
	}
	
	public static Object bindInit( String path, Object target ) throws Exception {
		String[] nodes = path.split( "\\." );
		for ( int k=0; k<nodes.length; k++ ) {
			Object temp = MethodHelper.invokeGetter( target, nodes[k] );
			if ( temp == null ) {
				temp = initEmptyField( nodes[k] , target );
			}
			target = temp;
		}
		return target;
	}
	
	public static boolean bind( String path, Object target, Object value, Class<?> paramType, boolean tryInit ) throws Exception {
		boolean bind = false;
		if ( value != null ) {
			if ( paramType == null ) {
				paramType = value.getClass();
			}
			String[] nodes = path.split( "\\." );
			String setter = nodes[ nodes.length-1 ];
			for ( int k=0; k<nodes.length-1; k++ ) {
				Object temp = MethodHelper.invokeGetter( target, nodes[k] );
				if ( temp == null && tryInit ) {
					temp = initEmptyField( nodes[k] , target );
				}
				target = temp;
			}
			MethodHelper.invokeSetter( target , setter, paramType, value );
			bind = true;
		}
		return bind;
	}
	
	public static boolean bind( String path, Object target, Object value ) throws Exception {
		return bind(path, target, value, null, false);
	}
	
	/**
	 * Lookup for a value by full method value (no get prefix is assumed)
	 * 
	 * If a method in path method is not found will raise exception 
	 * 
	 * @param path		the path in the object (ex. prop1.prop2)
	 * @param target	the target object
	 * @param continueOnNull	<code>true</code> to continue on null value and eventually raise an exception, <code>false</code> to return null when a null is encountered
	 * @return		the value found in the path
	 * @throws Exception	in case of exception
	 */
	public static Object lookupMethod( String path, Object target, boolean continueOnNull ) throws Exception {
		String[] nodes = path.split( "\\." );
		Object result = target;
		for ( int k=0; k<nodes.length && (continueOnNull || result != null); k++ ) {
			result = MethodHelper.invoke( result, nodes[k], MethodHelper.NO_PARAM_TYPES, MethodHelper.NO_PARAM_VALUES );
		}
		return result;
	}
	
	/**
	 * Lookup for a value by full method value (no get prefix is assumed)
	 * 
	 * If a method in path method is not found will raise exception
	 * If a method in path return null will stop and return null
	 * 
	 * @param path		the path in the object (ex. prop1.prop2)
	 * @param target	the target object
	 * @return		the value found in the path
	 * @throws Exception	in case of exception
	 */
	public static Object lookupMethod( String path, Object target ) throws Exception {
		return lookupMethod(path, target, EXIT_ON_NULL);
	}
	
	/**
	 * Lookup for a value by getter methods
	 * 
	 * @param path		the path in the object (ex. prop1.prop2)
	 * @param target	the target object
	 * @param continueOnNull	<code>true</code> to continue on null value and eventually raise an exception, <code>false</code> to return null when a null is encountered
	 * @return		the value found in the path
	 * @throws Exception	in case of exception
	 */
	public static Object lookup( String path, Object target, boolean continueOnNull ) throws Exception {
		String[] nodes = path.split( "\\." );
		Object result = target;
		for ( int k=0; k<nodes.length && (continueOnNull || result != null); k++ ) {
			result = MethodHelper.invokeGetter( result, nodes[k] );
		}
		return result;
	}
	
	/**
	 * Lookup for a value by getter methods ( return null when a null is encountered )
	 * 
	 * If a method in path method is not found will raise exception 
	 * 
	 * @param path		the path in the object (ex. prop1.prop2)
	 * @param target	the target object 
	 * @return		the value found in the path
	 * @throws Exception	in case of exception
	 */
	public static Object lookup( String path, Object target ) throws Exception {
		return lookup(path, target, EXIT_ON_NULL);
	}
	
	/**
	 * Lookup for a value by getter methods ( will continue on null value and eventually raise an exception)
	 * 
	 * If a method in path method is not found will raise exception
	 * If a method in path return null will stop and return null
	 * 
	 * @param path		the path in the object (ex. prop1.prop2)
	 * @param target	the target object
	 * @return		the value found in the path
	 * @throws Exception	in case of exception
	 */
	public static Object lookupContinueOnNull( String path, Object target ) throws Exception {
		return lookup(path, target, CONTINUE_ON_NULL);
	}

}
