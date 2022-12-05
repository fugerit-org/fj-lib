package org.fugerit.java.core.db.daogen;

import java.util.List;

public interface DaoResultList<T> extends DaoResult {

	List<T> getList();
	
	void evaluateResultFromList();
	
	void setSingleResult( T value );
	
	T getSingleResult();
	
}
