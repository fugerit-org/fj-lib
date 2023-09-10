package org.fugerit.java.core.db.dao.idgen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.helpers.DAOID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MysqlSeqIdGenerator extends BasicSeqIdGenerator {

	private String lastInsertIdFun;
	
	public MysqlSeqIdGenerator(String lastInsertIdFun) {
		super();
		this.lastInsertIdFun = lastInsertIdFun;
	}
	
	public MysqlSeqIdGenerator() {
		this( "SELECT LAST_INSERT_ID()" );
	}
	

	@Override
	protected String createSequenceQuery() {
		return this.lastInsertIdFun;
	}

	private String createUpdateQuery() {
		return "INSERT INTO "+this.getSequenceName()+" (ID) VALUES (NULL)";
	}
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicIdGenerator#generateId()
	 */
	@Override
	public DAOID generateId() throws DAOException {
		return DAOException.get( () -> {
			log.debug( "generateId start {}", this.getSequenceName() );
			DAOID id = null;
			try (  Connection conn = this.getConnectionFactory().getConnection();
					Statement stm = conn.createStatement() ) {
				stm.executeUpdate( this.createUpdateQuery() );
				try ( ResultSet rs = stm.executeQuery( this.createSequenceQuery() ) ) {
					if ( rs.next() ) {
						id = new DAOID( rs.getLong( 1 ) );	
					}
				}
			}
			log.debug( "generateId end : {}", id );
			return id;
		} );
	}

}
