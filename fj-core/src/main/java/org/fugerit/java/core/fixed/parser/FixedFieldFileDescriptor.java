package org.fugerit.java.core.fixed.parser;

import java.util.ArrayList;
import java.util.List;

public class FixedFieldFileDescriptor {

	public FixedFieldFileDescriptor( String id , String mode ) {
		super();
		this.id = id;
		this.mode = mode;
		this.listFields = new ArrayList<FixedFieldDescriptor>();
	}

	private String id;
	
	private String mode;
	
	private List<FixedFieldDescriptor> listFields;

	public String getId() {
		return id;
	}

	public String getMode() {
		return mode;
	}

	public List<FixedFieldDescriptor> getListFields() {
		return listFields;
	}

}
