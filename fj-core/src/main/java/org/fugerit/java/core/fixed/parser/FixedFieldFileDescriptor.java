package org.fugerit.java.core.fixed.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixedFieldFileDescriptor {

	public static final int NO_CHECK_LENGH = -1;
	
	public FixedFieldFileDescriptor( String id , String mode, String encoding ) {
		super();
		this.id = id;
		this.mode = mode;
		this.listFields = new ArrayList<>();
		this.validators = new HashMap<>();
		this.checkLengh = NO_CHECK_LENGH;
		this.encoding = encoding;
	}

	private String id;
	
	private String mode;
	
	private String endline;
	
	private String baseLocale;
	
	private String encoding;
	
	private List<FixedFieldDescriptor> listFields;
	
	private Map<String, FixedFileFieldValidator> validators;

	public boolean isCheckLengthActive() {
		return this.getCheckLengh() != NO_CHECK_LENGH;
	}
	
	public boolean isCustomEndlineActive() {
		return this.endline != null;
	}
	
	private int checkLengh;
	
	
	
	public String getBaseLocale() {
		return baseLocale;
	}

	public void setBaseLocale(String baseLocale) {
		this.baseLocale = baseLocale;
	}

	public int getCheckLengh() {
		return checkLengh;
	}

	public void setCheckLengh(int checkLengh) {
		this.checkLengh = checkLengh;
	}
	
	public String getEndline() {
		return endline;
	}
	
	public void setEndline(String endline) {
		this.endline = endline;
	}

	public String getId() {
		return id;
	}

	public String getMode() {
		return mode;
	}

	public synchronized String getEncoding() {
		return encoding;
	}
	
	public List<FixedFieldDescriptor> getListFields() {
		return listFields;
	}

	public Map<String, FixedFileFieldValidator> getValidators() {
		return validators;
	}

}
