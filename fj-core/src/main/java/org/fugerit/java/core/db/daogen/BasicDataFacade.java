package org.fugerit.java.core.db.daogen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.RSExtractor;

public class BasicDataFacade<T> extends BasicHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5321073652254215522L;

	private String tableName;

	private RSExtractor<T> rse;
	
	public String getTableName() {
		return tableName;
	}

	public RSExtractor<T> getRse() {
		return rse;
	}

	public BasicDataFacade(String tableName, RSExtractor<T> rse) {
		super();
		this.tableName = tableName;
		this.rse = rse;
	}

	public T loadById( DAOContext context, BigDecimal id ) throws DAOException {
		T model = null;
		BasicDAOHelper<T> daoHelper = new BasicDAOHelper<T>( context );
		SelectHelper query = daoHelper.newSelectHelper( this.getTableName() );
		query.andEqualParam( "id" , id );
		List<T> list = new ArrayList<>();
		daoHelper.loadAllHelper( list , query, this.getRse() );
		if ( list.size() == 1 ) {
			model = list.get( 0 );
		}
		return model;
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
