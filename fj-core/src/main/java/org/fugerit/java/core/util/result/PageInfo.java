package org.fugerit.java.core.util.result;

import java.io.Serializable;

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

	private int number;
	
	private int size;
	
	public PageInfo(int number, int size) {
		super();
		this.setNumber(number);
		this.setSize(size);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
