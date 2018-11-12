package org.fugerit.java.core.cfg.xml;

public class BasicTextValueType implements IdConfigType, TextValueType {

	private String id;
	
	private String textValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	
}
