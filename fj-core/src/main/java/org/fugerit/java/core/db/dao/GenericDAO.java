package org.fugerit.java.core.db.dao;

import java.util.List;

public class GenericDAO extends BasicDAO {

	public void loadAll(List l, String query, RSExtractor re) throws DAOException {
		this.loadAll(l, query, new FieldList( new FieldFactory() ), re);
	}
	
	public void loadAll(List l, String query, FieldList fields, RSExtractor re) throws DAOException {
		super.loadAll(l, query, fields, re);
	}

	public LoadResult loadAllResult(String query, FieldList fields, RSExtractor re) throws DAOException {
		return super.loadAllResult(query, fields, re);
	}

	protected GenericDAO(BasicDAOFactory daoFactory) {
		super(daoFactory);
	}

	public int update(OpDAO op) throws DAOException {
		return super.update(op);
	}

	public int update(String query, Field field) throws DAOException {
		return super.update(query, field);
	}

	public int update(String query, FieldList fields) throws DAOException {
		return super.update(query, fields);
	}

	public int update(String query) throws DAOException {
		return super.update(query);
	}
	
	

}
