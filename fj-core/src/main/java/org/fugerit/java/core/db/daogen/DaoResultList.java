package org.fugerit.java.core.db.daogen;

import java.util.List;

public interface DaoResultList<T> extends DaoResult {

	List<T> getList();
	
	void evaluateResultFromList();
	
	void setSingleResult( T value );
	
	/**
	 * Return the first element in the result list.
	 * 
	 * Note : if the result contains more than one element raises a RuntimeException.
	 * 
	 * @return	the first element in the result list or null if empty
	 */
	T getSingleResult();
	
	/**
	 * Return the first element in the result list.
	 * 
	 * Note : if the result contains more than one element raises a RuntimeException.
	 * 
	 * @return	the first element in the result list or null if empty
	 * 
	 * NOTE: this method is, by default, identical to getSingleResult().
	 */
	default T one() {
        return this.getSingleResult();
    }
	
}
