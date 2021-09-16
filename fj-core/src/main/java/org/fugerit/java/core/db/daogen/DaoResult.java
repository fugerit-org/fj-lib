package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.util.result.Result;

public interface DaoResult extends Result {

	String getResultDescription();
	
	void appendDescription( String v );
	
	void setResultDescription( String v );
	
	void setResult( DaoResult result);
	
	void setResult( int resultCode, String resultDescription );
	
}
