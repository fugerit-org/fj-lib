package org.fugerit.java.core.db.dao;

import java.util.List;


import org.fugerit.java.core.db.dao.rse.DoubleRSE;
import org.fugerit.java.core.db.dao.rse.LongRSE;
import org.fugerit.java.core.db.dao.rse.StringRSE;

public class DAOUtils {

	public DAOUtils( GenericDAO genericDAO ) {
		this.genericDAO = genericDAO;
	}
	
	private GenericDAO genericDAO;
	
	public Long extractLong( String sql ) throws DAOException {
		return (Long)this.genericDAO.loadOne( sql , LongRSE.DEFAULT );
	}
	
	public String extractString( String sql ) throws DAOException {
		return (String)this.genericDAO.loadOne( sql , StringRSE.DEFAULT );
	}
	
	public Double extractDouble( String sql ) throws DAOException {
		return (Double)this.genericDAO.loadOne( sql , DoubleRSE.DEFAULT );
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public List extractStringList( String sql ) throws DAOException {
		List list = this.genericDAO.newList();
		this.genericDAO.loadAll( list, sql , StringRSE.DEFAULT ); 
		return list;
	}
	
}

