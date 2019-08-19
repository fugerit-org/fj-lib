package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseIdFinder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8019457921226089217L;
	
	private BigDecimal id;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public void setId(long id) {
		this.id = new BigDecimal( id );
	}
	
}
