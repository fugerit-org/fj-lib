package org.fugerit.java.core.lang.helpers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AttributeHolderDefault implements Serializable, AttributesHolder {


	/**
	 * 
	 */
	private static final long serialVersionUID = -230640776936395216L;

	private Map<String, Object> map;
	
	public AttributeHolderDefault() {
		this.map = new HashMap<String, Object>();
	}

	public boolean containsAttribute(String key) {
		return map.containsKey(key);
	}

	@Override
	public Object getAttribute( String key ) {
		return map.get(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		 map.put(key, value);
	}

	public Object removeAttribute(Object key) {
		return map.remove(key);
	}
	
	public Set<String> keySet() {
		return map.keySet();
	}	
	
	public void setAll( Map<String, Object> attributeMap ) {
		this.map.putAll( attributeMap );
	}
	
	public Map<String, Object> toMap() {
		return new HashMap<String, Object>( this.map );
	}

}
