package org.fugerit.java.core.db.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.dao.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;

@Slf4j
public abstract class CloseableDAOContextAbstract implements CloseableDAOContext {
	
	private HashMap<String, Object> attributes;
	
	protected CloseableDAOContextAbstract() {
		super();
		this.attributes = new HashMap<>();
	}
	
	@Override
	public Object getAttribute(String key) {
		return this.attributes.get( key );
	}

	@Override
	public void setAttribute(String key, Object value) {
		this.attributes.put( key , value );
	}

	public static CloseableDAOContext newCloseableDAOContextCF(ConnectionFactory cf) {
		return new CloseableDAOContextAbstract() {
			@Override
			public Connection getConnection() throws DAOException {
				return cf.getConnection();
			}
			@Override
			public void close() throws Exception {
				cf.release();
			}
		};
	}

	public static CloseableDAOContext newCloseableDAOContextDS(DataSource ds) {
		return new CloseableDAOContextAbstract() {
			@Override
			public Connection getConnection() throws DAOException {
				return DAOException.get( ds::getConnection );
			}
			@Override
			public void close() throws Exception {
				log.debug( "close() doing nothing on datasource : {}", ds );
			}
		};
	}

}
