package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.lang.helpers.ClassHelper;

public class DataEntityUtils {

	private DataEntityUtils() {}
	
	public static DataEntityInfo unwrap( Object facade ) throws DAOException {
		DataEntityInfo res = null;
		if ( facade instanceof DataEntityInfo ) {
			res = (DataEntityInfo) facade;
		} else {
			throw new DAOException( "Facade : "+ClassHelper.toFullClassName( facade )+" does not implement "+DataEntityInfo.class.getName() );
		}
		return res;
	}
	
	public static void addToQuery( Object facade, QueryHelper query ) throws DAOException {
		DataEntityInfo info = unwrap( facade );
		query.appendToQueryWithSpace( info.getTableName() );
	}
	
}
