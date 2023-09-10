package org.fugerit.java.core.util.result;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class PageInfo implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2423423423421L;

	@Getter @Setter private int number;
	
	@Getter @Setter private int size;
	
	public PageInfo(int number, int size) {
		super();
		this.setNumber(number);
		this.setSize(size);
	}
	
}
