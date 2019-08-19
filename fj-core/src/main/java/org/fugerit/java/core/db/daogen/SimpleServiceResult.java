package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.fugerit.java.core.db.daogen.BasicDaoResult;

public class SimpleServiceResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3770076676911041800L;
	
	public final static int DEFAULT_OK = BasicDaoResult.RESULT_CODE_OK;
	public final static int DEFAULT_KO = BasicDaoResult.RESULT_CODE_KO;
	
	public final static String INFO_RESULT = "result";
	public final static String INFO_RESULT_MESSAGE = "resultMessage";
	
	public final static String INFO_ESITO_OK_NO_DATA_FOUND = String.valueOf( 2 );
	public final static String INFO_ESITO_OK_MULTIPLE_RESULTS = String.valueOf( 3 );
	
	public void addInfoEsito( String result, String resultMessage ) {
		this.getInfo().put( INFO_RESULT , result );
		this.getInfo().put( INFO_RESULT_MESSAGE , resultMessage );
	}
	
	public void addInfoNoDataFound() {
		this.addInfoEsito( INFO_ESITO_OK_NO_DATA_FOUND , "No data found" );
	}
	
	public void addInfoMultipleResult() {
		this.addInfoEsito( INFO_ESITO_OK_MULTIPLE_RESULTS , "Multiple results" );
	}
	
	public void addInfoDefaultOK() {
		this.addInfoEsito( String.valueOf( DEFAULT_OK ) , "OK" );
	}
	
	public void addInfoDefaultKO() {
		this.addInfoEsito( String.valueOf( DEFAULT_KO ) , "KO" );
	}
	
	private int result;
	
	private T content;
	
	private Map<String, Object> info;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public SimpleServiceResult(int result, T content) {
		super();
		this.result = result;
		this.content = content;
		this.info = new HashMap<>();
	}
	
	public SimpleServiceResult(int result) {
		this( result, null );
	}
	
	public SimpleServiceResult( T content ) {
		this( DEFAULT_OK, content );
	}

	public SimpleServiceResult() {
		
	}
	
	public Map<String, Object> getInfo() {
		return info;
	}
	
	public static <T> SimpleServiceResult<T> newDefaultResult( T data ) {
		SimpleServiceResult<T> res = null;
		if ( data == null ) {
			res = new SimpleServiceResult<T>( DEFAULT_KO );
		} else if ( data instanceof Collection ) {
			Collection<?> c = (Collection<?>) data;
			if ( c.isEmpty() ) {
				res = new SimpleServiceResult<T>( DEFAULT_KO );	
			} else {
				res = new SimpleServiceResult<T>( DEFAULT_OK, data );
			}
		} else {
			res = new SimpleServiceResult<T>( DEFAULT_OK, data );
		}
		return res;
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+"[result:"+result+"]";
	}

	
}
