package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.FieldList;

public class QueryHelper extends BasicHelper {

	private static final long serialVersionUID = -5078435202864156086L;

	private StringBuilder query;
	
	private FieldList fields;
	
	private String table;
	
	public StringBuilder getQuery() {
		return query;
	}

	public FieldList getFields() {
		return fields;
	}
	
	public String getTable() {
		return table;
	}
	
	public QueryHelper( String table, FieldList fl ) {
		this.query = new StringBuilder();
		this.fields = fl;
		this.table = table;
	}
	
	public String getQueryContent() {
		return this.getQuery().toString();
	}
	
}
