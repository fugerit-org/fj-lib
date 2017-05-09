package org.fugerit.java.core.db.connect;

import java.sql.Connection;

import java.sql.Driver;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.lang.helpers.ClassHelper;

public class DbcpConnectionFactory extends ConnectionFactoryImpl {

	private BasicDataSource dataSource;

	public DbcpConnectionFactory( String drv, String url, String usr, String pwd, int init, int min, int max ) throws DAOException {
		try {
			Driver driver = (Driver)ClassHelper.newInstance( drv );
			this.dataSource = new BasicDataSource();
			this.dataSource.setDriverClassName( drv );
			this.dataSource.setUrl( url );
			this.dataSource.setUsername( usr );
			this.dataSource.setPassword( pwd );
			this.dataSource.setMaxActive( max );
			this.dataSource.setMaxIdle( min );
			this.dataSource.setInitialSize( init );
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
