package org.fugerit.java.core.db.connect;

import java.sql.Connection;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.metadata.DataBaseInfo;

public class SingleConnectionFactory implements ConnectionFactory {

	public SingleConnectionFactory(Connection conn) {
		super();
		this.conn = conn;
	}

	private Connection conn;
	
	@Override
	public DataBaseInfo getDataBaseInfo() throws DAOException {
		DataBaseInfo info = new DataBaseInfo();
		info.init( this.getConnection() );
		return info;
	}

	@Override
	public void release() throws DAOException {
		DAOException.apply( () -> this.getConnection().close() );
	}

	@Override
	public Connection getConnection() throws DAOException {
		return this.conn;
	}
	
}
