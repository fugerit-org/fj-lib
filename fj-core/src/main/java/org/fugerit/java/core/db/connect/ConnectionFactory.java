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
 * <p>Simple class for creating java.sql.Connection.</p>
 * 
 * @author Fugerit
 */
public interface ConnectionFactory {
	
	/**
	 * Return database Metadata wrapper
	 * 
	 * @return 	data base metadata
	 * @throws DAOException		in case of issues
	 */
	public DataBaseInfo getDataBaseInfo() throws DAOException;
	
	/**
	 * Release the connection factory
	 * 
	 * @throws DAOException		in case of issues
	 */
	public void release() throws DAOException;
	
	/**
	 * Return a connection from the factory
	 * 
	 * @return					the java.sql.Connection
	 * @throws DAOException		in case of issues
	 */
	public Connection getConnection() throws DAOException;
	
}
