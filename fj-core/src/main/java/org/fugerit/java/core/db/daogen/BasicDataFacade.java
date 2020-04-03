package org.fugerit.java.core.db.daogen;

import java.math.BigDecimal;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.db.dao.idgen.BasicSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.IdGeneratorFacade;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicDataFacade<T> extends BasicHelper implements DataEntityInfo {

	protected static Logger logger = LoggerFactory.getLogger( BasicDataFacade.class );
	
	public String getSequenceName() {
		return null;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5321073652254215522L;

	private String tableName;
	
	private String queryView;

	private RSExtractor<T> rse;
	
	private BasicSeqIdGenerator idGenerator;
	
	public BigDecimal generateId( DAOContext context ) throws DAOException {
		BigDecimal id = null;
		if ( this.idGenerator == null && this.getSequenceName() != null ) {
			this.idGenerator = IdGeneratorFacade.sequenceGenerator( context.getConnection() , this.getSequenceName() );
		}
		if ( this.idGenerator != null ) {
			DAOID daoid = idGenerator.generateId( context.getConnection() );
			id = new BigDecimal( daoid.longValue() );
		}
		return id;
	}

	public String getTableName() {
		return tableName;
	}

	public RSExtractor<T> getRse() {
		return rse;
	}
	

	public String getQueryView() {
		return queryView;
	}

	public BasicDataFacade(String tableName, RSExtractor<T> rse, String queryView) {
		super();
		this.tableName = tableName;
		this.rse = rse;
		if ( StringUtils.isNotEmpty( queryView ) ) {
			this.queryView = queryView;
		} else {
			this.queryView = null;
		}
	}
	
	public BasicDataFacade(String tableName, RSExtractor<T> rse) {
		this( tableName, rse, null );
	}

	public BasicDaoResult<T> loadAll( DAOContext context ) throws DAOException {
		BasicDaoResult<T> result = new BasicDaoResult<T>();
		BasicDAOHelper<T> daoHelper = new BasicDAOHelper<T>( context );
		SelectHelper query = daoHelper.newSelectHelper( this.getTableName() );
		daoHelper.loadAllHelper( result.getList() , query, this.getRse() );
		result.evaluateResultFromList();
		return result;
	}
	
	public void evaluteSqlUpdateResult( int res, T model, BasicDaoResult<T> result ) {
		if ( res > 0 ) {
			result.setResult( BasicDaoResult.RESULT_CODE_OK , "Operation OK" );
			if ( model != null ) {
				result.getList().add( model );	
			}
		} else {
			result.setResult( BasicDaoResult.RESULT_CODE_KO , "Operation KO" );
		}
	}
	
}
