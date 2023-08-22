package org.fugerit.java.core.db.daogen;

import java.sql.Connection;

import org.fugerit.java.core.db.dao.DAOException;

public class CloseableDAOContextSC extends CloseableDAOContextAbstract {

	private Connection conn;

	public CloseableDAOContextSC(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public Connection getConnection() throws DAOException {
		return this.conn;
	}

	@Override
	public void close() throws Exception {
		this.conn.close();
	}

}
