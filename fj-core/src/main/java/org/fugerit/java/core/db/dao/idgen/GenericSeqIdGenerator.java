package org.fugerit.java.core.db.dao.idgen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.IdGenerator;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.db.helpers.DbUtils;

public class GenericSeqIdGenerator extends BasicSeqIdGenerator {
	
	private IdGenerator idGenerator; 
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#generateId()
	 */
	@Override
	public DAOID generateId() throws DAOException {
		DAOID id = null;
		if ( this.idGenerator == null ) {
			id = super.generateId();
		} else {
			id = this.idGenerator.generateId();
		}
		return id;
	}

	private int dbType;
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#configure(java.util.Properties)
	 */
	@Override
	public void configure(Properties props) throws ConfigException {
		super.configure(props);
		if ( this.getConnectionFactory() == null ) {
			throw new ConfigException( "Devi associare la connection factory prima di chiamare configure()" );
		} else {
			Connection conn = null;
			try {
				conn = this.getConnectionFactory().getConnection();
				this.dbType = DbUtils.indentifyDB( conn );
				if ( this.dbType == DbUtils.DB_SQLSERVER ) {
					this.idGenerator = new SqlServerSeqIdGenerator();
					this.idGenerator.setConnectionFactory( this.getConnectionFactory() );
					this.idGenerator.configure( props );
				} else if ( this.dbType == DbUtils.DB_MYSQL ) {
					this.idGenerator = new MysqlSeqIdGenerator();
					this.idGenerator.setConnectionFactory( this.getConnectionFactory() );
					this.idGenerator.configure( props );					
				}
			} catch (Exception e) {
				throw ( new ConfigException( e ) );
			} finally {
				if ( conn != null ) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#createSequenceQuery()
	 */
	@Override
	protected String createSequenceQuery() {
		String sql = null;
		if ( this.dbType == DbUtils.DB_ORACLE ) {
			sql = OracleSeqIdGenerator.createSequenceQuery( this.getSequenceName() );
		} else if ( this.dbType == DbUtils.DB_POSTGRESQL ) {
			sql = PostgresqlSeqIdGenerator.createSequenceQuery( this.getSequenceName() );
		} else {
			// tipo db sconosciuto
		}
		return sql;
	}

}
