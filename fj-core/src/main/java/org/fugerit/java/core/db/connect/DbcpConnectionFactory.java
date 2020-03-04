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
package org.fugerit.java.core.db.connect;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.fugerit.java.core.db.dao.DAOException;

/**
 * ConnectionFactory implementation based on a DBCP 1.4
 * 
 * @author Fugerit
 *
 */
public class DbcpConnectionFactory extends ConnectionFactoryImpl {

	private BasicDataSource dataSource;

	/**
	 * Constructor
	 * 
	 * @param drv		driver type
	 * @param url		jdbc url
	 * @param usr		user
	 * @param pwd		password
	 * @param init		initial connection
	 * @param min		minimum connection
	 * @param max		maximum connection	
	 * @throws DAOException		in case of issues
	 */
	public DbcpConnectionFactory( String drv, String url, String usr, String pwd, int init, int min, int max ) throws DAOException {
		this(drv, url, usr, pwd, init, min, max, null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param drv		driver type
	 * @param url		jdbc url
	 * @param usr		user
	 * @param pwd		password
	 * @param init		initial connection
	 * @param min		minimum connection
	 * @param max		maximum connection	
	 * @param cl		the class loader
	 * @throws DAOException		in case of issues
	 */
	public DbcpConnectionFactory( String drv, String url, String usr, String pwd, int init, int min, int max, ClassLoader cl ) throws DAOException {
		try {
			this.dataSource = new BasicDataSource();
			this.dataSource.setDriverClassName( drv );
			this.dataSource.setUrl( url );
			this.dataSource.setUsername( usr );
			this.dataSource.setPassword( pwd );
			this.dataSource.setMaxActive( max );
			this.dataSource.setMaxIdle( min );
			this.dataSource.setInitialSize( init );
			if ( cl != null ) {
				this.dataSource.setDriverClassLoader( cl );
			}
		} catch (Exception e) {
			throw new DAOException( e );
		}
	}
	
	@Override
	public Connection getConnection() throws DAOException {
		Connection conn = null;
		try {
			conn = this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new DAOException( e );
		}
		return conn;
	}

	
}
