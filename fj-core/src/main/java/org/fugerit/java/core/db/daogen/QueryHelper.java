package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.FieldList;

/**
 * 
 * QueryHelper is basically a wrapper which encapsulates information needed to setup a <code>java.sql.PreparedStatement</code>.
 * 
 * This is achieved by use of a query buffer (getQuery()) and a list of field (getFields())
 * 
 * Here is a very simple example : 
 * 
 * <code>
 * 		QueryHelper helper = new QueryHelper( "sample_table", new FieldList() );
 * 		helper.get
 * </code>
 * 
 * @author Matteo Franci a.k.a. Fugerit
 *
 */
public class QueryHelper extends BasicHelper {

	private static final long serialVersionUID = -5078435202864156086L;

	/**
	 * Constant for '('
	 */
	public static final String OPEN_PARA = " ( ";
	
	/**
	 * Constant for ')'
	 */
	public static final String CLOSE_PARA = " ) ";
	
	/**
	 * Constant for '?'
	 */
	public static final String QUESTION_MARK = " ? ";
	
	/**
	 * Constant for ' '
	 */
	public static final String WHITESPACE = " ";
	
	/**
	 * Add '(' to query buffer
	 */
	public void openPara() {
		this.appendToQuery( OPEN_PARA );
	}

	/**
	 * Add ')' to query buffer
	 */
	public void closePara() {
		this.appendToQuery( CLOSE_PARA );
	}
	
	private StringBuilder query;
	
	private FieldList fields;
	
	private String table;
	
	/**
	 * Getter method for the query buffer
	 * 
	 * @return the query buffer
	 */
	public StringBuilder getQuery() {
		return query;
	}

	/**
	 * Getter method for the field list
	 * 
	 * @return	the field list
	 */
	public FieldList getFields() {
		return fields;
	}
	
	public String getTable() {
		return table;
	}

	public QueryHelper append( String s ) {
		this.getQuery().append( s );
		return this;
	}

	public QueryHelper appendWithSpace( String s ) {
		return this.append( WHITESPACE ).append( s ).append( WHITESPACE );
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
	
	/**
	 * Builds the query context of all query buffer.
	 * (To be preferred to getQuery().toString() method).
	 * 
	 * @return	the current query content
	 */
	public String getQueryContent() {
		return this.getQuery().toString();
	}
	
}
