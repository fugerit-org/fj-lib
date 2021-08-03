package org.fugerit.java.core.db.daogen;

import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.util.result.BasicResult;

public class BasicDaoResult<T> extends BasicResult implements DaoResultList<T> {

	public static final int RESULT_NODATAFOUND = -3;
	public static final int RESULT_NOT_SET = Integer.MIN_VALUE;
	
	public BasicDaoResult(int resultCode, String resultDescription) {
		this( resultCode );
		this.resultDescription = resultDescription;
	}
	
	public BasicDaoResult(int resultCode) {
		super(resultCode);
		this.list = new ArrayList<T>();
	}

	public BasicDaoResult() {
		this( RESULT_NOT_SET );
	}
	
	private List<T> list;

	@Override
	public List<T> getList() {
		return list;
	}

	private String resultDescription;
	
	@Override
	public String getResultDescription() {
		return this.resultDescription;
	}
	
	@Override
	public void setResultDescription( String v ) {
		this.resultDescription = v;
	}
	
	@Override
	public void evaluateResultFromList() {
		if ( this.getList().isEmpty() ) {
			this.setResultCode( RESULT_NODATAFOUND );
		} else {
			this.setResultCode( RESULT_CODE_OK );
		}
	}
	
	@Override
	public void setResult( DaoResult result)  {
		this.setResult( result.getResultCode(), result.getResultDescription() );
	}
	
	@Override
	public void setResult( int resultCode, String resultDescription )  {
		this.setResultCode( resultCode );
		this.setResultDescription( resultDescription );
	}
	
	@Override
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
	@Override
	public T getSingleResult() {
		T res = null;
		if ( this.getList().size() == 1 ) {
			res = this.getList().get( 0 );
		} else if ( this.getList().size() > 1 ) {
			throw new RuntimeException( "Multiple results : "+this.getList().size() );
		}
		return res;
	}
	
	@Override
	public String toString() {
		return super.toString()+"[resultDescription"+this.getResultDescription()+",size:"+this.getList().size()+"]";
	}
	
}
