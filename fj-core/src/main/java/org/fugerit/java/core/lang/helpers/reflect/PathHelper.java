package org.fugerit.java.core.lang.helpers.reflect;

/**
 * Simple class to look up a property path in a java object
 * 
 * @author fugerit
 *
 */
public class PathHelper {

	public static Object lookup( String path, Object target ) throws Exception {
		String[] nodes = path.split( "\\." );
		Object result = target;
		for ( int k=0; k<nodes.length; k++ ) {
			result = MethodHelper.invokeGetter( result, nodes[k] );
		}
		return result;
	}
	
}
