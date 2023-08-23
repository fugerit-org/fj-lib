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

import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>A implementation of java.util.List interface able to save a collections in a Map too.</p>
 * 
 * @author Fugerit
 *
 * @param <K>	the class type for the key
 * @param <T>	the class type for the value
 */
public class ListMap<K,T> extends AbstractList<T> implements Serializable {
	
	/*
	 * Add mode  STRICT ( raise a runtime exception in case of duplicate keys )
	 */
	public static final int ADD_MODE_STRICT = 1;
	
	/*
	 * Add mode LOOSE ( allow for key duplicate)
	 */
	public static final int ADD_MODE_LOOSE = 2;
	
	/*
	 * Default add mode (LOOSE)
	 */
	public static final int ADD_MODE_DEFAULT = ADD_MODE_LOOSE;
	
	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	/*
	 * 
	 */
	private static final long serialVersionUID = -10202423525249408L;
	
	private List<T> list;
	private Map<K, T> map;
	private KeyMapper<K,T> keyMapper;
	private int addMode;

	/**
	 * Get the KeyMapper for this ListMap
	 * 
	 * @return the KeyMapper	
	 */
	public KeyMapper<K, T> getKeyMapper() {
		return keyMapper;
	}

	/**
	 * Set the KeyMapper for this ListMap
	 * 
	 * @param keyMapper	the KeyMapper
	 */
	public void setKeyMapper(KeyMapper<K, T> keyMapper) {
		this.keyMapper = keyMapper;
	}

	
	public ListMap( KeyMapper<K,T> keyMapper, int addMode ) {
		this.list = new ArrayList<T>();
		this.map = new HashMap<K, T>();
		this.keyMapper = keyMapper;
		this.addMode = addMode;
	}
	
	public ListMap( KeyMapper<K,T> keyMapper ) {
		this( keyMapper, ADD_MODE_DEFAULT ); 
	}
	
	public ListMap( int addMode ) {
		this( null, addMode ); 
	}
	
	/**
	 * Default constructor, create an Empty ListMap
	 * 
	 */
	public ListMap() {
		this( null );
	}
	
	@SuppressWarnings("unchecked")
	private K getKey( T element ) {
		K key = null;
		if ( this.keyMapper != null ) {
			key = this.keyMapper.createKey( element );
		} else  if ( element instanceof KeyObject<?> ) {
			Object currentKey = ((KeyObject<?>) element).getKey();
			key = ((K)currentKey);	
		} else {
			throw new RuntimeException( "No key rule for object : "+element );
		}
		return key;
	}
	
	@Override
	public void add(int index, T element) {
		if ( this.getAddMode() == ADD_MODE_STRICT && this.map.containsKey( this.getKey( element ) ) ) {
			throw new RuntimeException( "Key already exists for element : "+element );
		} else {
			this.putWorker( element );
			this.list.add(index, element);
		}
	}

	@Override
	public T remove(int index) {
		T res = this.list.remove(index);
		this.removeWorker( res );
		return res;
	}

	@Override
	public T set(int index, T element) {
		this.removeWorker( element );
		this.putWorker( element );
		return this.list.set( index, element );
	}

	@Override
	public T get(int index) {
		return this.list.get( index );
	}

	@Override
	public int size() {
		return this.list.size();
	}
 
	/**
	 * Get an object by key
	 * 
	 * Delegate for getMap().get().
	 * 
	 * @param key		the key
	 * @return			the value
	 */
	public T get( Object key ) {
		return this.map.get( key );
	}

	/**
	 * Alias for get(key)
	 * 
	 * @param key	the key
	 * @return		the value
	 */
	public T getProperty( K key ) {
		return this.get( key );
	}
	
	/**
	 * Return iteration over Keys
	 * 
	 * @return	iterations over keys
	 */
	public Iterator<K> getKeyIterator() {
		return this.map.keySet().iterator();
	}
	
	/**
	 * The list underlying Map
	 * 	
	 * @return The key/value Map for this ListMap
	 */
	public Map<K, T> getMap() {
		return Collections.unmodifiableMap( this.map );
	}

	/**
	 * Get the object adding policy for this List.
	 * 
	 * @return the AddMode
	 */
	public int getAddMode() {
		return addMode;
	}

	/**
	 * Set the object adding policy for this List
	 * 
	 * ListMap has an underlying Map, the policy states of duplicate keys are allowed 
	 * 
	 * @param addMode	the policy
	 */
	public void setAddMode(int addMode) {
		this.addMode = addMode;
	}

	private void putWorker( T element ) {
		this.map.put( this.getKey( element ) , element );
	}
	
	private void removeWorker( T element ) {
		this.map.remove( this.getKey( element ) );
	}
	
}

