package org.fugerit.java.core.cfg.xml;

import java.io.Serializable;

import org.fugerit.java.core.util.collection.KeyString;

public class BasicIdConfigType implements IdConfigType, KeyString, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5048275081450375435L;
	
	private String id;
	
	@Override
	public String getKey() {
		return this.id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[id:"+this.getId()+"]";
	}
	
}
