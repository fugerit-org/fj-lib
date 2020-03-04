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

import java.io.Serializable;

/**
 * <p>Interface defining a policy for mapping a key to a value.</p>
 * 
 * <p>This interface is used in ListMap to associates keys to values when adding and getting elements.</p>
 * 
 * @author Fugerit
 *
 * @param <K>	the type class for the key
 * @param <T>	the type class for the value
 */
public interface KeyMapper<K,T> extends Serializable {
	public K createKey( T key );
}
