/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.dao.rse;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.RSExtractor;

/**
 * Result Set Extractor for query where just one column should be get.
 * 
 * Can be used the column name or the column index.
 * 
 * Default is using column index at position 1.
 * 
 * @author Fugerit
 *
 */
public abstract class SingleColumnRSE<K> implements RSExtractor<K> {

	public static final int DEFAULT_COLUMN_INDEX = 1;
	
	public static final int USE_NAME_INDEX = -1;
	
	private String columnName;
	
	private int columnIndex;
	
	private SingleColumnRSE( int index, String name ) {
		this.columnIndex = index;
		this.columnName = name;
	}
	
	public SingleColumnRSE( int index ) {
		this( index, null );
	}
	
	public SingleColumnRSE( String name ) {
		this( USE_NAME_INDEX, name );
	}

	public SingleColumnRSE() {
		this( DEFAULT_COLUMN_INDEX, null );
	}
	
	public String getColumnName() {
		return columnName;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public boolean isUseColumnIndex() {
		return this.getColumnIndex() != USE_NAME_INDEX;
	}
	
	public boolean isUseColumnName() {
		return this.getColumnIndex() == USE_NAME_INDEX;
	}

	protected abstract K convert( Object o );
	
	@Override
	public K extractNext( ResultSet rs ) throws SQLException {
		Object result = null;
		if ( this.isUseColumnIndex() ) {
			result = rs.getObject( this.getColumnIndex() );
		} else {
			result = rs.getObject( this.getColumnName() );
		}
		K value = null;
		if ( result != null ) {
			value = this.convert( result );
			// at this point the value should not be null.
			if ( value == null ) {
				throw new RuntimeException( "Failed object conversion : "+result );
			}
		}
		return value;
	}

}
