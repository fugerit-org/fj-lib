package org.fugerit.java.core.db.helpers;

import java.sql.Connection;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAOException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author Fugerit
 *
 */
@Slf4j
public class DbUtils {

	private DbUtils() {}
	
	public static final int DB_UNKNOWN = 0;
	
	public static final int DB_POSTGRESQL = 100;

	public static final int DB_MYSQL = 200;
	
	public static final int DB_ORACLE = 300;

	public static final int DB_SQLSERVER = 500;

	public static final int DB_H2 = 600;

	public static final int DB_HSQLDB = 700;

	public static void close( Connection conn ) throws DAOException {
		if ( conn != null ) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DAOException( e );
			}
		}
	}
	
	public static int indentifyDB( String productName ) {
		int dbType = DB_UNKNOWN;
		String name = productName.toLowerCase();
		log.info( "Configuring Database specific logic, productName : {}", name );
		if ( name.indexOf( "postgres" ) != -1 ) {
			dbType = DB_POSTGRESQL;
			log.info( "db type : POSTGRESQL ({})", dbType );
		} else if ( name.indexOf( "oracle" ) != -1 ) {
			dbType = DB_ORACLE;
			log.info( "db type : ORACLE ({})", dbType );
		} else if ( name.indexOf( "sqlserver" ) != -1 ) {
			dbType = DB_SQLSERVER;
			log.info( "db type : SQLSERVER ({})", dbType );
		} else if ( name.indexOf( "h2" ) != -1 ) {
			dbType = DB_H2;
			log.info( "db type : H2 ({})", dbType );
		} else if ( name.indexOf( "mysql" ) != -1 ) {
			dbType = DB_MYSQL;
			log.info( "db type : MYSQL ({})", dbType );
		} else if ( name.indexOf( "hsql" ) != -1 ) {
			dbType = DB_HSQLDB;
			log.info( "db type : HSQLDB ({}) was ({})", dbType, name );
		} else {
			log.info( "Unknown db type ({})", dbType );
		}
		return dbType;
	}
	
	public static int indentifyDB( Connection conn ) throws SQLException {
		String name = conn.getMetaData().getDatabaseProductName().toLowerCase();
		return indentifyDB( name );
	}
	
}
