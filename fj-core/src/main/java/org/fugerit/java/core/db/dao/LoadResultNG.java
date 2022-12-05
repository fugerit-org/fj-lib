package org.fugerit.java.core.db.dao;

import java.io.Closeable;

public interface LoadResultNG<T> extends Closeable {

	boolean hasNext() throws DAOException;
	
	T next() throws DAOException;
	
	long getCount();

}
