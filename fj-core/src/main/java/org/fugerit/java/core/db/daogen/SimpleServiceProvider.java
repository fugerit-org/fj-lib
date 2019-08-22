package org.fugerit.java.core.db.daogen;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.fugerit.java.core.db.dao.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleServiceProvider<T> extends BasicHelper {

	protected static Logger logger = LoggerFactory.getLogger( SimpleServiceProvider.class );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -258759771993319363L;

	public static void throwDAOException( Exception e ) throws DAOException {
		throw new DAOException( e );
	}
	
	protected abstract CloseableDAOContext newDefaultContext() throws DAOException;

	public Response createResponseFromList( SimpleServiceResult<List<T>> result ) {
		Response res = null;
		if ( result.getContent() != null ) {
			res = Response.ok( result.getContent() ).build();
		}
		return res;
	}

	public Response createResponseFromObject( SimpleServiceResult<T> result ) {
		Response res = null;
		List<T> list = new ArrayList<>();
		if ( result.getContent() != null ) {
			list.add( result.getContent() );
			res = Response.ok( list ).build();
		}
		return res;
	}
	
}
