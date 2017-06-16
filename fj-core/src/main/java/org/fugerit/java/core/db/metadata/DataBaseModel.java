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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author Fugerit
 *
 */
public class DataBaseModel {
	
	private String databaseProductName;
	
	private String databaseProductVersion;
	
	private String driverName;
	
	private String driverVersion;
	
	public void addTable( TableModel tableModel ) {
		this.getTableList().add( tableModel );
		this.getTableMap().put( tableModel.getTableId(), tableModel );
		this.getTableNameMap().put( tableModel.getName(), tableModel );
	}
	
	public DataBaseModel() {
		this.tableList = new ArrayList<TableModel>();
		this.tableMap = new HashMap<TableId, TableModel>();
		this.tableNameMap = new HashMap<String, TableModel>();
	}
	
	private List<TableModel> tableList;

	private Map<TableId, TableModel> tableMap;
	
	private Map<String, TableModel> tableNameMap;
	
	/*
	 * @return Restituisce il valore di tableList.
	 */
	public List<TableModel> getTableList() {
		return tableList;
	}

	/*
	 * @return Restituisce il valore di tableMap.
	 */
	public Map<TableId, TableModel> getTableMap() {
		return tableMap;
	}

	/*
	 * @return the tableNameMap
	 */
	public Map<String, TableModel> getTableNameMap() {
		return tableNameMap;
	}
	
	/*
	 * @return the databaseProductName
	 */
	public String getDatabaseProductName() {
		return databaseProductName;
	}

	/*
	 * @param databaseProductName the databaseProductName to set
	 */
	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	/*
	 * @return the databaseProductVersion
	 */
	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}

	/*
	 * @param databaseProductVersion the databaseProductVersion to set
	 */
	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}

	/*
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/*
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/*
	 * @return the driverVersion
	 */
	public String getDriverVersion() {
		return driverVersion;
	}

	/*
	 * @param driverVersion the driverVersion to set
	 */
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}
	
	
}
