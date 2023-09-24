package org.fugerit.java.core.db.daogen;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Stream;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.db.dao.idgen.BasicSeqIdGenerator;
import org.fugerit.java.core.db.dao.idgen.IdGeneratorFacade;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.result.Result;
import org.slf4j.Logger;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicDataFacade<T> extends BasicHelper implements DataEntityInfo {

	protected static Logger logger = log;	// for backward compatibility
	
	public String getSequenceName() {
		return null;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5321073652254215522L;

	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	@Getter private String tableName;
	
	@Getter private String queryView;

	@Getter private RSExtractor<T> rse;
	
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
		BasicDaoResult<T> result = new BasicDaoResult<>();
		BasicDAOHelper<T> daoHelper = new BasicDAOHelper<>( context );
		SelectHelper query = daoHelper.newSelectHelper( this.getTableName() );
		daoHelper.loadAllHelper( result.getList() , query, this.getRse() );
		result.evaluateResultFromList();
		return result;
	}
	
	public Stream<T> loadAllStream( DAOContext context ) throws DAOException {
		return this.loadAll(context).stream();
	}
	
	public void evaluteSqlUpdateResult( int res, T model, BasicDaoResult<T> result ) {
		if ( res > 0 ) {
			result.setResult( Result.RESULT_CODE_OK , "Operation OK" );
			if ( model != null ) {
				result.getList().add( model );	
			}
		} else {
			result.setResult( Result.RESULT_CODE_KO , "Operation KO" );
		}
	}
	
}
