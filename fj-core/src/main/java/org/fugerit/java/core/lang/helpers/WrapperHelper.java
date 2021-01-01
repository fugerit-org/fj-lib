package org.fugerit.java.core.lang.helpers;

/**
 * 
 * Collections of utility method for handling Wrappers.
 * 
 * @author Matteo a.k.a. Fugerit
 * @since 0.7.4.8
 * @see Wrapper
 *
 */
public class WrapperHelper {

	/**
	 * Recursively unwrap one object as long as it's implementing Wrapper interface
	 * 
	 * @param <T>	the type of the object to be unwrapped
	 * @param model	the object to be unwrapped
	 * @return		the unwrapped model
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unwrap( T model ){
		T res = model;
		if ( res != null ) {
			while ( res instanceof Wrapper ) {
				res = ((Wrapper<T>)res).unwrapModel();
			}
		}
		return res;
	}
	
}
