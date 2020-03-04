/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.util.collection;

/**
 * <p>Interface for an Object which has a key property.</p>
 * 
 * @author Fugerit
 *	
 * @param <K>	the type of the key
 */
public interface KeyObject<K> {

	/**
	 * Returns the key of the object
	 * 
	 * @return	the key for this object
	 */
	public K getKey();
	
}
