/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.metadata;

import java.util.List;
import java.util.Map;

import org.fugerit.java.core.util.collection.ListMap;


/**
 * 
 *
 * @author Fugerit
 *
 */
public class TableModel extends ColumnContainer {
	
	public TableModel() {
		this.indexList = new ListMap<>();
		this.foreignKeyList = new ListMap<>();
	}
	
	private ListMap<String, ForeignKeyModel> foreignKeyList;
	
	private ListMap<String, IndexModel> indexList;
		
	private TableId tableId;
	
	private IndexModel primaryKey;
	
	private String comment;

	public void addForeignKey( ForeignKeyModel foreignKeyModel ) {
		this.getForeignKeyList().add( foreignKeyModel );
	}	
	
	public void addIndex( IndexModel indexModel ) {
		this.getIndexList().add( indexModel );
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public IndexModel getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(IndexModel primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public TableId getTableId() {
		return tableId;
	}

	public void setTableId(TableId tableId) {
		this.tableId = tableId;
	}

	public String getName() {
		return this.getTableId().getTableName();
	}
	
	public String getCatalog() {
		return this.getTableId().getTableCatalog();
	}	

	public String getSchema() {
		return this.getTableId().getTableSchema();
	}	
	
	public Map<String, IndexModel> getIndexMap() {
		return this.indexList.getMap();
	}

	public List<IndexModel> getIndexList() {
		return this.indexList;
	}

	public Map<String, ForeignKeyModel> getForeignKeyMap() {
		return this.foreignKeyList.getMap();
	}
	
	public List<ForeignKeyModel> getForeignKeyList() {
		return this.foreignKeyList;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"["+this.getTableId()+"]";
	}
	
	
	
}
