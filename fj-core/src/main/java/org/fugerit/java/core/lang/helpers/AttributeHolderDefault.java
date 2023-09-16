package org.fugerit.java.core.lang.helpers;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AttributeHolderDefault implements AttributesHolder, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3304679466443785878L;
	
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
	
	private HashMap<String, Object> map;
	
	public AttributeHolderDefault() {
		this.map = new HashMap<>();
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
		return new HashMap<>( this.map );
	}

}
