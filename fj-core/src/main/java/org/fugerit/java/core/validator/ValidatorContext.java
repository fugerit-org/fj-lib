package org.fugerit.java.core.validator;

import java.util.Locale;
import java.util.Properties;

import org.fugerit.java.core.lang.helpers.AttributeHolderDefault;

public class ValidatorContext extends AttributeHolderDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6713242595883142448L;

	private ValidatorCatalog catalog;
	
	private ValidatorResult result;
	
	private Locale locale;
	
	private String fieldId;
	
	private String value;
	
	private String label;

	public ValidatorCatalog getCatalog() {
		return catalog;
	}

	public void setCatalog(ValidatorCatalog catalog) {
		this.catalog = catalog;
	}

	public ValidatorResult getResult() {
		return result;
	}

	public void setResult(ValidatorResult result) {
		this.result = result;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ValidatorContext(ValidatorCatalog catalog, ValidatorResult result, Locale locale, String fieldId, String value, String label) {
		super();
		this.catalog = catalog;
		this.result = result;
		this.locale = locale;
		this.fieldId = fieldId;
		this.value = value;
		this.label = label;
	}

	public Properties getBundle() {
		return this.getCatalog().getBundle( this.getLocale() );
	}
	
}
