package org.fugerit.java.core.util;

import java.io.Serializable;
import java.util.Map;

public class MapEntry<K, V> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3192113156465029159L;

	private K key;
	
	private V value;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public MapEntry(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public MapEntry() {
		this( null, null );
	}

	public <M extends Map<K, V>> V put( M map ) {
		return map.put( key , value );
	}
		
	public String toString() {
		return ObjectUtils.toDefaultString( this , 
				new PropertyEntry( "key", String.valueOf( this.getKey() ) ),
				new PropertyEntry( "value", String.valueOf( this.getValue() ) ) );
	}
	
}
