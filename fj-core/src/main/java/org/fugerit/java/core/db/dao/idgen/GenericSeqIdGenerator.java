package org.fugerit.java.core.db.dao.idgen;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.IdGenerator;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.lang.helpers.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericSeqIdGenerator extends BasicSeqIdGenerator {

	private IdGenerator idGenerator; 
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#generateId()
	 */
	@Override
	public DAOID generateId() throws DAOException {
		return this.idGenerator.generateId();
	}

	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#configure(java.util.Properties)
	 */
	@Override
	public void configure(Properties props) throws ConfigException {
		super.configure(props);
		if ( this.getConnectionFactory() == null ) {
			throw new ConfigException( "Must associate factory before invoking configure()" );
		} else if ( StringUtils.isEmpty( this.getSequenceName() ) ) {
			throw new ConfigException( "Sequence name myst be set" );
		} else {
			this.idGenerator = ConfigException.get( () -> IdGeneratorFacade.sequenceGenerator(getConnectionFactory(), this.getSequenceName() ) );
		}
		log.trace( "test createSequenceQuery() : {}", this.createSequenceQuery() );
	}

	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#createSequenceQuery()
	 */
	@Override
	protected String createSequenceQuery() {
		return null;
	}

}
