package org.fugerit.java.core.cfg.xml;

public class BasicTextValueType extends BasicIdConfigType implements TextValueType {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5768454871360291378L;
	
	private String textValue;

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	
}
