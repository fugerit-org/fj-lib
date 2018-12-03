package org.fugerit.java.core.util.filterchain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MiniFilterContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -379329829073614664L;

	private Map<String, Object> map;
	
	public MiniFilterContext() {
		this.map = new HashMap<String, Object>();
	}

	public boolean containsAttribute(String key) {
		return map.containsKey(key);
	}

	public Object getAttribute(Object key) {
		return map.get(key);
	}

	public Object setAttribuite(String key, Object value) {
		return map.put(key, value);
	}

	public Object removeAttribute(Object key) {
		return map.remove(key);
	}
	
	public Set<String> keySet() {
		return map.keySet();
	}	
		
}
