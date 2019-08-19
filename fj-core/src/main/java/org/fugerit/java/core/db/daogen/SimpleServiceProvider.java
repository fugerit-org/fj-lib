package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleServiceProvider extends BasicHelper {

	protected static Logger logger = LoggerFactory.getLogger( SimpleServiceProvider.class );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -258759771993319363L;

	public static void throwDAOException( Exception e ) throws DAOException {
		throw new DAOException( e );
	}
	
	protected abstract CloseableDAOContext newDefaultContext() throws DAOException;
	
}
