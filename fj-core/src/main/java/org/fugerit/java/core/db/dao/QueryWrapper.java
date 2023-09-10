package org.fugerit.java.core.db.dao;

public abstract class QueryWrapper {

	public abstract String createPagedQuery(String sql, PageInfoDB info);

}
