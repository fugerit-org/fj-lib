package org.fugerit.java.core.db.daogen;

import java.util.HashMap;

public abstract class CloseableDAOContextAbstract implements CloseableDAOContext {
	
	private HashMap<String, Object> attributes;
	
	public CloseableDAOContextAbstract() {
		super();
		this.attributes = new HashMap<>();
	}
	
	@Override
	public Object getAttribute(String key) {
		return this.attributes.get( key );
	}

	@Override
	public void setAttribute(String key, Object value) {
		this.attributes.put( key , value );
	}

}
