package org.fugerit.java.core.db.dao.idgen;

import java.io.InputStream;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.IdGenerator;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.log.BasicLogObject;
import org.w3c.dom.Element;

public abstract class BasicIdGenerator extends BasicLogObject implements IdGenerator {
	
	public BasicIdGenerator() {
		this.autoCloseConnection = false;
	}
	
	/* (non-Javadoc)
	 * @see org.opinf.jlib.std.cfg.ConfigurableObject#configureProperties(java.io.InputStream)
	 */
	public void configureProperties(InputStream source) throws ConfigException {
		
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.std.cfg.ConfigurableObject#configureXML(java.io.InputStream)
	 */
	public void configureXML(InputStream source) throws ConfigException {
		
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.std.cfg.ConfigurableObject#configure(org.w3c.dom.Element)
	 */
	public void configure(Element tag) throws ConfigException {
		
	}

	/* (non-Javadoc)
	 * @see org.opinf.jlib.std.cfg.ConfigurableObject#configure(java.util.Properties)
	 */
	public void configure(Properties props) throws ConfigException {
		
	}

	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.IdGenerator#generateID()
	 */
	public abstract DAOID generateId() throws DAOException;
	
	private ConnectionFactory connectionFactory;

	/**
	 * @return the connectionFactory
	 */
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/**
	 * @param connectionFactory the connectionFactory to set
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	private boolean autoCloseConnection;

	public boolean isAutoCloseConnection() {
		return autoCloseConnection;
	}

	public void setAutoCloseConnection(boolean autoCloseConnection) {
		this.autoCloseConnection = autoCloseConnection;
	}

}