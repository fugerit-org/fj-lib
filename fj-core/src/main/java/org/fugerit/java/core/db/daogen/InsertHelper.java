package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.FieldList;

public class InsertHelper extends QueryHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4923486564445809747L;

	private StringBuilder queryParams;
		
	private boolean firstParam;
	
	public InsertHelper( String table, FieldList fl ) {
		super(table, fl);
		this.firstParam = true;
		this.getQuery().append( "INSERT INTO " );
		this.getQuery().append( this.getTable() );
		this.queryParams = new StringBuilder();
	}

	public String getQueryContent() {
		StringBuilder result = new StringBuilder();
		result.append( super.getQueryContent() );
		result.append( " ) VALUES " );
		result.append( this.queryParams.toString() );
		result.append( " )" );
		return result.toString();
	}

	public void addParam( String columnName, Object value ) {
		if ( value != null ) {
			if ( this.firstParam ) {
				this.getQuery().append( "( " );
				this.queryParams.append( "( ? " );
				this.firstParam = false;
			} else {
				this.getQuery().append( ", " );
				this.queryParams.append( ", ? " );
			}
			this.getQuery().append( columnName );			
			this.getFields().addField( value );	
		}
	}
	
}
