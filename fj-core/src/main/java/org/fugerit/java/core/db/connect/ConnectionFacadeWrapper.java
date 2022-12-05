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

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.metadata.DataBaseInfo;

/**
 * 
 * Incapsulate a ConnectionFactory
 *
 * @author Fugerit
 *
 */
public class ConnectionFacadeWrapper implements ConnectionFactory {

	private ConnectionFactory wrapperConnectionFactory;
	
	public ConnectionFacadeWrapper( ConnectionFactory wrapperConnectionFactory ) {
		this.wrapperConnectionFactory = wrapperConnectionFactory;
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactoryImpl#getDataBaseInfo()
	 */
	@Override
	public DataBaseInfo getDataBaseInfo() throws DAOException {
		return this.getWrapperConnectionFactory().getDataBaseInfo();
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactoryImpl#release()
	 */
	@Override
	public void release() throws DAOException {
		this.getWrapperConnectionFactory().release();
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.connect.ConnectionFactoryImpl#getConnection()
	 */
	@Override
	public Connection getConnection() throws DAOException {
		return this.getWrapperConnectionFactory().getConnection();
	}

	/*
	 * @return the wrapperConnectionFactory
	 */
	public ConnectionFactory getWrapperConnectionFactory() {
		return wrapperConnectionFactory;
	}

}
