package org.fugerit.java.core.util;

import java.util.AbstractMap;
import java.util.Map;

import org.fugerit.java.core.util.collection.KeyObject;

public class MapEntry<K, V> implements KeyObject<K> {

	private AbstractMap.SimpleEntry<K, V> entry;

	@Override
	public K getKey() {
		return this.entry.getKey();
	}

	public void setKey(K key) {
		this.entry = new AbstractMap.SimpleEntry<>( key, this.getValue() );
	}

	public V getValue() {
		return this.entry.getValue();
	}

	public void setValue(V value) {
		this.entry.setValue( value );
	}

	public MapEntry(K key, V value) {
		super();
		this.entry = new AbstractMap.SimpleEntry<>( key, value );
	}

	public MapEntry() {
		this( null, null );
	}

	public <M extends Map<K, V>> V put( M map ) {
		return map.put( this.getKey(), this.getValue() );
	}
		
	@Override
	public String toString() {
		return ObjectUtils.toDefaultString( this , 
				new PropertyEntry( "key", String.valueOf( this.getKey() ) ),
				new PropertyEntry( "value", String.valueOf( this.getValue() ) ) );
	}
	
}
