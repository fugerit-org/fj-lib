/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.dao;

import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.db.dao.rse.DoubleRSE;
import org.fugerit.java.core.db.dao.rse.LongRSE;
import org.fugerit.java.core.db.dao.rse.StringRSE;
import org.fugerit.java.core.log.BasicLogObject;

public class DAOUtils extends BasicLogObject {

	public DAOUtils( DAOFactory bdf ) {
		this.basicDAOFactory = bdf;
	}
	
	private DAOFactory basicDAOFactory;
	
	public Long extractLong( String sql ) throws DAOException {
		return DAOHelper.loadOne( sql, BasicDAO.NO_FIELDS, LongRSE.DEFAULT, this.basicDAOFactory, this );
	}
	
	public String extractString( String sql ) throws DAOException {
		return DAOHelper.loadOne( sql, BasicDAO.NO_FIELDS, StringRSE.DEFAULT, this.basicDAOFactory, this );
	}
	
	public Double extractDouble( String sql ) throws DAOException {
		return DAOHelper.loadOne( sql, BasicDAO.NO_FIELDS, DoubleRSE.DEFAULT, this.basicDAOFactory, this );
	}

	public List<String> extractStringList( String sql ) throws DAOException {
		List<String> list = new ArrayList<String>();
		DAOHelper.loadAll( list , sql, BasicDAO.NO_FIELDS, StringRSE.DEFAULT, this.basicDAOFactory, this );
		return list;
	}
	
}

