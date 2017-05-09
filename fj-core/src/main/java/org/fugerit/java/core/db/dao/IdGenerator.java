package org.fugerit.java.core.db.dao;

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
