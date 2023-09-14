package org.fugerit.java.core.db.metadata;

import org.fugerit.java.core.db.dao.DAOException;

public interface JdbcAdaptor {
	
	String getTableComment( TableId tableId ) throws DAOException;
	
 	String getColumnComment( TableId tableId, String columnName ) throws DAOException;
	
 	String getColumnExtraInfo( TableId tableId, String columnName ) throws DAOException;
	
}