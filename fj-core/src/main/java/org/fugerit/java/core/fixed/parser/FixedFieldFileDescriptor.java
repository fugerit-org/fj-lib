package org.fugerit.java.core.fixed.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixedFieldFileDescriptor {

	public FixedFieldFileDescriptor( String id , String mode ) {
		super();
		this.id = id;
		this.mode = mode;
		this.listFields = new ArrayList<FixedFieldDescriptor>();
		this.validators = new HashMap<String, FixedFileFieldValidator>();
	}

	private String id;
	
	private String mode;
	
	private List<FixedFieldDescriptor> listFields;
	
	private Map<String, FixedFileFieldValidator> validators;

	public String getId() {
		return id;
	}

	public String getMode() {
		return mode;
	}

	public List<FixedFieldDescriptor> getListFields() {
		return listFields;
	}

	public Map<String, FixedFileFieldValidator> getValidators() {
		return validators;
	}

}
