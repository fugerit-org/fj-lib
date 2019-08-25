package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class CloseableDAOContextAbstract implements CloseableDAOContext, Serializable {

	private Map<String, Object> attributes;
	
	public CloseableDAOContextAbstract() {
		super();
		this.attributes = new HashMap<>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4166164994631000182L;

	@Override
	public Object getAttribute(String key) {
		return this.attributes.get( key );
	}

	@Override
	public void setAttribute(String key, Object value) {
		this.attributes.put( key , value );
	}

}
