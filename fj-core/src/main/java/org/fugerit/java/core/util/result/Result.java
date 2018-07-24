package org.fugerit.java.core.util.result;

import java.util.Map;

public interface Result {

	/*
	 * Result code
	 */
	
	public static final int RESULT_CODE_OK = 0;
	
	public static final int RESULT_CODE_KO = -1;
	
	public void setResultCode( int resultCode );
	
	public int getResultCode();
	
	public boolean isResultOk();

	public Map<String, Object> getInfoMap();
	
}
