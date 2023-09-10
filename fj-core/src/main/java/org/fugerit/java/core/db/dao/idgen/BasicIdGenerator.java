package org.fugerit.java.core.db.dao.idgen;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.IdGenerator;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

public abstract class BasicIdGenerator implements IdGenerator {

	protected BasicIdGenerator() {
		this.autoCloseConnection = false;
	}

	@Override
	public void configure(Properties props) throws ConfigException {
		// do nothing impl
	}

	@Override
	public void configureProperties(InputStream source) throws ConfigException {
		ConfigException.apply( () -> this.configure( PropsIO.loadFromStream( source ) ) );
	}

	@Override
	public void configureXML(InputStream source) throws ConfigException {
		ConfigException.apply( () -> this.configure( DOMIO.loadDOMDoc( source ).getDocumentElement() ) );
	}
	
	@Override
	public void configure(Element tag) throws ConfigException {
		SafeFunction.applyIfNotNull( tag , () -> configure( DOMUtils.attributesToProperties( tag ) ) );
	}

	@Override
	public abstract DAOID generateId( Connection conn ) throws DAOException;
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.IdGenerator#generateID()
	 */
	@Override
	public DAOID generateId() throws DAOException {
		return this.generateId( this.getConnectionFactory().getConnection() );
	}
	
	private ConnectionFactory connectionFactory;

	/**
	 * @return the connectionFactory
	 */
	@Override
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/**
	 * @param connectionFactory the connectionFactory to set
	 */
	@Override
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
