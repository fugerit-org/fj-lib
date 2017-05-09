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
package org.fugerit.java.core.db.metadata;

import java.util.List;
import java.util.Map;

import org.fugerit.java.core.util.collection.ListMap;

/**
 * <p>Wrapper for a collection of ColumnModel.</p>
 *
 * <p>Note : column name are considered without cases as for standard SQL.</p>
 *
 * @author Fugerit
 *
 */
public class ColumnContainer {

	private ListMap<String, ColumnModel> columnList;
	
	/**
	 * Returns the column list
	 * 
	 * @return the column list
	 */
	public List<ColumnModel> getColumnList() {
		return this.columnList;
	}

	/**
	 * Returns the column map
	 * 
	 * @return	the column map
	 */
	public Map<String, ColumnModel> getColumnMap() {
		return this.columnList.getMap();
	}	
	
	public ColumnContainer() {
		this.columnList = new ListMap<String, ColumnModel>();
	}
	
	/**
	 * Add a column to the container
	 * 
	 * @param columnModel	the column model to add
	 */
	public void addColumn( ColumnModel columnModel ) {
		if ( columnModel != null ) {
			this.columnList.add( columnModel );
		}
	}	
	
	/**
	 * Get a column from the container (by name)
	 * 
	 * @param columnName	the column name
	 * @return				the column model or null
	 */
	public ColumnModel getColumn( String columnName ) {
		if ( columnName != null ) {
			columnName = columnName.toLowerCase();
		}
		ColumnModel columnModel = (ColumnModel)this.getColumnMap().get( columnName );
		return columnModel;
	}
	
}
