package org.fugerit.java.core.db.daogen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.util.result.ResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleServiceProvider<T> extends BasicHelper {

	protected static Logger logger = LoggerFactory.getLogger( SimpleServiceProvider.class );
	

	public static final String SDF_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -258759771993319363L;

	public static void throwDAOException( Exception e ) throws DAOException {
		throw new DAOException( e );
	}
	
	protected abstract CloseableDAOContext newDefaultContext() throws DAOException;

	public Object createResultFromList( SimpleServiceResult<List<T>> result ) {
		return result.getContent();
	}
	
	public Object createResultFromObject( SimpleServiceResult<T> result ) {
		return ResultHelper.createList( result.getContent() );
	}
			
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
	
	/**
	 * Use same format of XMLGregorianCalendar
	 * 
	 * @param s		input string
	 * @return		the date
	 * @throws Exception	in case of any error
	 */
	public Date defaultConvertToUtilDate( String s ) throws Exception {
		Date r = null;
		if ( s != null ) {
			XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar( s );
			r = result.toGregorianCalendar().getTime();
		}
		return r;
	}
	 
}
