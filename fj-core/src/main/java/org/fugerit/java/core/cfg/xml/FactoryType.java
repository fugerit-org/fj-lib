package org.fugerit.java.core.cfg.xml;

import org.w3c.dom.Element;

public class FactoryType extends BasicIdConfigType {

	@Override
	public String toString() {
		return "FactoryType [type=" + type + ", info=" + info + ", unsafe=" + unsafe + ", element=" + element + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4156411727576276222L;

	private String type;
	
	private String info;
	
	private String unsafe;
	
	private String unsafeMode;

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
	
	public String getUnsafe() {
		return unsafe;
	}

	public void setUnsafe(String unsafe) {
		this.unsafe = unsafe;
	}
	
	public String getUnsafeMode() {
		return unsafeMode;
	}

	public void setUnsafeMode(String unsafeMode) {
		this.unsafeMode = unsafeMode;
	}

	private Element element;

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

}
