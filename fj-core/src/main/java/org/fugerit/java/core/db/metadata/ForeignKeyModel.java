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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.util.collection.KeyObject;

/*
 * 
 *
 * @author Fugerit
 *
 */
public class ForeignKeyModel implements KeyObject<String>  {

	public ForeignKeyModel() {
		this.columnMap = new Properties();
	}
	
	private Properties columnMap;
	
	private String name;

	private TableId foreignTableId;
	

	/*
	 * @return Restituisce il valore di foreignTableId.
	 */
	public TableId getForeignTableId() {
		return foreignTableId;
	}

	/*
	 * @param foreignTableId il valore di foreignTableId da impostare.
	 */
	public void setForeignTableId(TableId foreignTableId) {
		this.foreignTableId = foreignTableId;
	}

	/*
	 * @return Restituisce il valore di name.
	 */
	public String getName() {
		return name;
	}

	/*
	 * @param name il valore di name da impostare.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Properties getColumnMap() {
		return columnMap;
	}

	public List<ColumnModel> foreignColumnList( TableModel tableModel ) {
		List<ColumnModel>  columnList = new ArrayList<ColumnModel>();
		Enumeration<Object> e = columnMap.keys();
		while ( e.hasMoreElements() ) {
			String columnName = (String)(e.nextElement());
			columnList.add( tableModel.getColumn( columnName ) );
		}
		return columnList;
	}
	
	public List<ColumnModel> internalColumnList( TableModel tableModel ) {
		List<ColumnModel> columnList = new ArrayList<ColumnModel>();
		Enumeration<Object> e = columnMap.elements();
		while ( e.hasMoreElements() ) {
			String columnName = (String)(e.nextElement());
			columnList.add( tableModel.getColumn( columnName ) );
		}
		return columnList;
	}

	@Override
	public String getKey() {
		return this.getName().toLowerCase();
	}	
	
	
	
}
