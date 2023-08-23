package org.fugerit.java.core.db.daogen;

import java.io.Serializable;

public class GenericIdFinder<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -224608299860106085L;
	
	private T id;

	public T getId() {
		return id;
	}

	public void setId( T id) {
		this.id = id;
	}
	
}
