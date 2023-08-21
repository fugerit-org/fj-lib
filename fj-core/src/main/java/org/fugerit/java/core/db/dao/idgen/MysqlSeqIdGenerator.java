package org.fugerit.java.core.db.dao.idgen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.helpers.DAOID;

public class MysqlSeqIdGenerator extends BasicSeqIdGenerator {

	public static String createSequenceQuery( String seqName) {
		return " SELECT nextval('"+seqName+"'); ";
	}
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#createSequenceQuery()
	 */
	@Override
	protected String createSequenceQuery() {
		return createSequenceQuery( this.getSequenceName() ); 
	}

	private String createUpdateQuery() {
		return "UPDATE "+this.getSequenceName()+" SET id=LAST_INSERT_ID(id+1);";
	}
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicIdGenerator#generateId()
	 */
	@Override
	public DAOID generateId() throws DAOException {
		this.getLogger().debug( "generateId start " );
		DAOID id = null;
		try (  Connection conn = this.getConnectionFactory().getConnection();
				Statement stm = conn.createStatement() ) {
			stm.executeUpdate( this.createUpdateQuery() );
			try ( ResultSet rs = stm.executeQuery( " SELECT LAST_INSERT_ID(); " ) ) {
				if ( rs.next() ) {
					id = new DAOID( rs.getLong( 1 ) );	
				}
			}
		} catch (Exception e) {
			throw ( new DAOException( e ) );
		}
		this.getLogger().debug( "generateId end : "+id );
		return id;
	}

}
