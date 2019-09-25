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
package org.fugerit.java.core.db.dao;

import java.sql.Connection;

import org.fugerit.java.core.cfg.ConfigurableObject;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.helpers.DAOID;


/*
 * 
 *
 * @author Fugerit
 *
 */
public interface IdGenerator extends ConfigurableObject {
	
	public DAOID generateId( Connection conn ) throws DAOException;
	
	public DAOID generateId() throws DAOException;
	
	/*
	 * @return the connectionFactory
	 */
	public ConnectionFactory getConnectionFactory();

	/*
	 * @param connectionFactory the connectionFactory to set
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory);
	
}
