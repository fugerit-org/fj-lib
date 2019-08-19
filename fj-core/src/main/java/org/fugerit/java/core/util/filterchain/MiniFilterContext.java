package org.fugerit.java.core.util.filterchain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.fugerit.java.core.lang.helpers.AttributesHolder;

public class MiniFilterContext implements Serializable , AttributesHolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -379329829073614664L;

	private Map<String, Object> map;
	
	private Properties customConfig;

	
	public MiniFilterContext() {
		this.map = new HashMap<String, Object>();
		this.customConfig = new Properties();
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
	
	public Properties getCustomConfig() {
		return customConfig;
	}

	@Deprecated
	public void setAttribuite(String key, Object value) {
		this.setAttribute(key, value);
	}

}
