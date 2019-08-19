package org.fugerit.java.core.db.daogen;

import java.sql.Connection;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.lang.helpers.AttributesHolder;

public interface DAOContext extends AttributesHolder {

	public Connection getConnection() throws DAOException;

}
