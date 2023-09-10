package org.fugerit.java.core.db.dao.idgen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.db.dao.CloseDAOHelper;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.function.SafeFunction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BasicSeqIdGenerator extends BasicIdGenerator {

	protected abstract String createSequenceQuery();
	
	private String sequenceName;
	
	public static final String PROP_SEQ_NAME = "sequenceName";

	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicIdGenerator#configure(java.util.Properties)
	 */
	@Override
	public void configure(Properties props) throws ConfigException {
		this.setSequenceName( props.getProperty( PROP_SEQ_NAME ) );
	}	
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicIdGenerator#generateID()
	 */
	@Override
	public DAOID generateId( Connection conn ) throws DAOException {
		return DAOException.get(() ->{
			log.debug( "generateId start " );
			DAOID id = null;
			try {
				String sql = this.createSequenceQuery();
				log.debug( "generateId query : '{}'", sql );
				try ( Statement stm = conn.createStatement();
						ResultSet rs = stm.executeQuery( sql ) ) {
					if ( rs.next() ) {
						id = new DAOID( rs.getLong( 1 ) );
					}
				}
			} finally {
				SafeFunction.applyOnCondition( this::isAutoCloseConnection , () -> CloseDAOHelper.close( conn ) );
			}
			log.debug( "generateId end : {}", id );
			return id;
		});
	}

	/**
	 * @return the sequenceName
	 */
	public String getSequenceName() {
		return sequenceName;
	}

	/**
	 * @param sequenceName the sequenceName to set
	 */
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

}
