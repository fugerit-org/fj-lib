package org.fugerit.java.core.db.daogen;

import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.util.result.BasicResult;

public class BasicDaoResult<T> extends BasicResult {

	public static final int RESULT_NODATAFOUND = -3;
	public static final int RESULT_NOT_SET = Integer.MIN_VALUE;
	
	public BasicDaoResult(int resultCode) {
		super(resultCode);
		this.list = new ArrayList<T>();
	}

	public BasicDaoResult() {
		this( RESULT_NOT_SET );
	}
	
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	private String resultDescription;
	
	public String getResultDescription() {
		return this.resultDescription;
	}
	
	public void setResultDescription( String v ) {
		this.resultDescription = v;
	}
	
	public void evaluateResultFromList() {
		if ( this.getList().isEmpty() ) {
			this.setResultCode( RESULT_NODATAFOUND );
		} else {
			this.setResultCode( RESULT_CODE_OK );
		}
	}
	
	public void setResult( int resultCode, String resultDescription )  {
		this.setResultCode( resultCode );
		this.setResultDescription( resultDescription );
	}
	
	public void setSingleResult( T value ) {
		this.getList().set( 0 , value );
	}
	
	/**
	 * Return the first element in the result list.
	 * 
	 * Note : if the result contains more than one element raises a RuntimeException.
	 * 
	 * @return	the first element in the result list or null if empty
	 */
	public T getSingleResult() {
		T res = null;
		if ( this.getList().size() == 1 ) {
			res = this.getList().get( 0 );
		} else if ( this.getList().size() > 1 ) {
			throw new RuntimeException( "Multiple results : "+this.getList().size() );
		}
		return res;
	}
	
}
