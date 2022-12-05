package org.fugerit.java.core.cfg.xml;

import org.w3c.dom.Element;

public class FactoryType extends BasicIdConfigType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4156411727576276222L;

	private String type;
	
	private String info;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	private Element element;

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

}
