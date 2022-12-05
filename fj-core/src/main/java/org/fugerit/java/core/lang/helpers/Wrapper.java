package org.fugerit.java.core.lang.helpers;

/**
 * 
 * Interface for wrapping / unwrapping objects
 * 
 * @author Matteo a.k.a. Fugerit
 * @since 0.7.4.8
 *
 * @param <T>	the type of the object to wrap
 */
public interface Wrapper<T> {

	/**
	 * Unwrap the underlying object
	 * 
	 * @return	the wrapped object
	 */
	public T unwrapModel();

	/**
	 * 
	 * Wraps the give objects
	 * 
	 * @param wrapped	objects to wrap
	 */
	public void wrapModel(T wrapped);
	
	/**
	 * Recursively unwrap the wrapped object until it is not instance of Wrapper anymore.
	 * 
	 * @return	the underlying object
	 */
	public T unwrap();
	
}
