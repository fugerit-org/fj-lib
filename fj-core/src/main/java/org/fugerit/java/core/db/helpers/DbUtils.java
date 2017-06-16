package org.fugerit.java.core.db.helpers;

import java.sql.Connection;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.log.LogFacade;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class DbUtils {

	public static final int DB_UNKNOWN = 0;
	
	public static final int DB_POSTGRESQL = 100;

	public static final int DB_MYSQL = 200;
	
	public static final int DB_ORACLE = 300;
	
	public static final int DB_DB2 = 400;
	
	public static final int DB_SQLSERVER = 500;
	
	public static void close( Connection conn ) throws DAOException {
		if ( conn != null ) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DAOException( e );
			}
		}
	}
	
	public static int indentifyDB( Connection conn ) throws SQLException {
		int dbType = DB_UNKNOWN;
		String name = conn.getMetaData().getDriverName().toLowerCase();
		LogFacade.getLog().info( "Configuring Database specific logic, driver : "+name );
		if ( name.indexOf( "postgres" ) != -1 ) {
			dbType = DB_POSTGRESQL;
			LogFacade.getLog().info( "IdGenerator configured for : POSTGRESQL ("+dbType+")" );
		} else if ( name.indexOf( "oracle" ) != -1 ) {
			dbType = DB_ORACLE;
			LogFacade.getLog().info( "IdGenerator configured for : ORACLE ("+dbType+")" );
		} else if ( name.indexOf( "sqlserver" ) != -1 ) {
			dbType = DB_SQLSERVER;
			LogFacade.getLog().info( "IdGenerator configured for : SQLSERVER ("+dbType+")" );
		} else if ( name.indexOf( "mysql" ) != -1 ) {
			dbType = DB_MYSQL;
			LogFacade.getLog().info( "IdGenerator configured for : MYSQL ("+dbType+")" );
		} else {
			dbType = DB_UNKNOWN;
			LogFacade.getLog().info( "Unknown db type ("+dbType+")" );
		}
		return dbType;
	}
	
}
