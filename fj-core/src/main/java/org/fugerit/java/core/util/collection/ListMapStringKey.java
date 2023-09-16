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

import java.util.Collection;

/**
 * <p>Specialization of ListMap using String as keys.</p>
 * 
 * @author Fugerit
 *
 * @param <T>	the class type for the contained objects
 */
public class ListMapStringKey<T> extends ListMap<String, T> {

	/*
	 * 
	 */
	private static final long serialVersionUID = -3178662537960623081L;

	@Override
	public int hashCode() {
		// super class implementation is ok
		return super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		// super class implementation is ok - is equals if all contained elements are equals
		return super.equals(o);
	}
	
	/**
	 * Default constructor
	 * 
	 */
	public ListMapStringKey() {
		super();
	}
	
	public ListMapStringKey( Collection<T> c ) {
		this.addAll( c );
	}

	public ListMapStringKey(int addMode) {
		super(addMode);
	}

	public ListMapStringKey(KeyMapper<String, T> keyMapper, int addMode) {
		super(keyMapper, addMode);
	}

	public ListMapStringKey(KeyMapper<String, T> keyMapper) {
		super(keyMapper);
	}
	
	
}
