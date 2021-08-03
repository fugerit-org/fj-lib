package org.fugerit.java.core.util.result;

import java.util.HashMap;
import java.util.Map;

import org.fugerit.java.core.log.BasicLogObject;

public class BasicResult extends BasicLogObject implements Result {

	public static final Result DEFAULT_OK_RESULT = new BasicResult( RESULT_CODE_OK );
	
	public static final Result DEFAULT_KO_RESULT = new BasicResult( RESULT_CODE_KO );
	
	private int resultCode;
	
	private Map<String, Object> infoMap;
	
	public BasicResult( int resultCode ) {
		this.resultCode = resultCode;
		this.infoMap = new HashMap<String, Object>();
	}
		
	@Override
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	@Override
	public int getResultCode() {
		return resultCode;
	}

	@Override
	public boolean isResultOk() {
		return this.getResultCode() == RESULT_CODE_OK;
	}

	@Override
	public Map<String, Object> getInfoMap() {
		return infoMap;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[resultCode:"+this.getResultCode()+"]";
	}
	
}
