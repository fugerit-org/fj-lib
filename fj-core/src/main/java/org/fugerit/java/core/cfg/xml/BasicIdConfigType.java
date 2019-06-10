package org.fugerit.java.core.cfg.xml;

import java.io.Serializable;

import org.fugerit.java.core.util.collection.KeyObject;

public class BasicIdConfigType implements IdConfigType, KeyObject<String>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5048275081450375435L;
	
	private String id;
	
	@Override
	public String getKey() {
		return this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
