package org.fugerit.java.core.db.daogen;

import java.math.BigDecimal;

public class BaseIdFinder extends GenericIdFinder<BigDecimal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8019457921226089217L;
	
	public void setId(long id) {
		this.setId( new BigDecimal( id ) );
	}
	
}
