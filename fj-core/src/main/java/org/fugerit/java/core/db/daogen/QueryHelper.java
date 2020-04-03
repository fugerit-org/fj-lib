package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.FieldList;

public class QueryHelper extends BasicHelper {

	private static final long serialVersionUID = -5078435202864156086L;

	public static final String OPEN_PARA = " ( ";
	
	public static final String CLOSE_PARA = " ) ";
	
	public static final String QUESTION_MARK = " ? ";
	
	public void openPara() {
		this.appendToQuery( OPEN_PARA );
	}
	
	public void closePara() {
		this.appendToQuery( CLOSE_PARA );
	}
	
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
	
	public void appendToQuery( String s ) {
		this.getQuery().append( s );
	}

	public void appendToQueryWithSpace( String s ) {
		this.appendToQuery( " " );
		this.appendToQuery( s );
		this.appendToQuery( " " );
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
