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
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
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
	 * 
	 * @param cfProps		connections parameters
	 * @throws DAOException	in case of issues
	 */
	public DbcpConnectionFactory( Properties cfProps ) throws DAOException {
		this(cfProps, null);
	}
	
	/**
	 * 
	 * @param cfProps		connections parameters
	 * @param cl			to use for driver class loading
	 * @throws DAOException	in case of issues
	 */
	public DbcpConnectionFactory( Properties cfProps, ClassLoader cl ) throws DAOException {
		DAOException.apply( () -> {
			int init = Integer.parseInt( cfProps.getProperty( PROP_CF_EXT_POOLED_SC , "3" ) );
			int min = Integer.parseInt( cfProps.getProperty( PROP_CF_EXT_POOLED_IC , "10" ) );
			int max = Integer.parseInt( cfProps.getProperty( PROP_CF_EXT_POOLED_MC , "30" ) );
			this.dataSource = new BasicDataSource();
			this.dataSource.setDriverClassName( cfProps.getProperty( PROP_CF_MODE_DC_DRV ) );
			this.dataSource.setUrl( cfProps.getProperty( PROP_CF_MODE_DC_URL ) );
			this.dataSource.setUsername( cfProps.getProperty( PROP_CF_MODE_DC_USR ) );
			this.dataSource.setPassword( cfProps.getProperty( PROP_CF_MODE_DC_PWD ) );
			this.dataSource.setMaxTotal( max );
			this.dataSource.setMaxIdle( min );
			this.dataSource.setInitialSize( init );
			if ( cl != null ) {
				this.dataSource.setDriverClassLoader( cl );
			}
		});
	}
	
	@Override
	public Connection getConnection() throws DAOException {
		return DAOException.get( () -> this.dataSource.getConnection() );
	}

}
