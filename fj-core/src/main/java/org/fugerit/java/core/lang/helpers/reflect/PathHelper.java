package org.fugerit.java.core.lang.helpers.reflect;

/**
 * Simple class to look up a property path in a java object
 * 
 * @author fugerit
 *
 */
public class PathHelper {

	public static final boolean CONTINUE_ON_NULL = true;
	
	public static final boolean EXIT_ON_NULL = false;
	
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
	 * @param continueOnNull	<code>true</code> if the s, <code>false</code> 
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
