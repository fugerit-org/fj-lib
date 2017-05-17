/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.dao;


import java.sql.Connection;

import org.fugerit.java.core.db.connect.ConnectionFactory;

/**
 * <p>Basic DAO Factory.</p>
 *
 * @author Fugerit
 */
public class BasicDAOFactory implements DAOFactory {

	private DAOUtils daoUtils;
	
	public Object[] sqlArgs = new Object[0];
	
    /* (non-Javadoc)
	 * @see org.fugerit.java.core.db.dao.DAOFactory#getConnection()
	 */
    @Override
	public Connection getConnection() throws DAOException {
        return this.connectionFactory.getConnection();
    }
    
    private ConnectionFactory connectionFactory;
    
    private FieldFactory fieldFactory;
    
    public BasicDAOFactory(ConnectionFactory cFactory) {
    	this( cFactory, new FieldFactory() );
    }    
    
    public BasicDAOFactory( ConnectionFactory cFactory, FieldFactory fFactory ) {
        super();
        this.connectionFactory = cFactory;
        this.fieldFactory = fFactory;
        this.daoUtils = new DAOUtils( this );
    }

    /* (non-Javadoc)
	 * @see org.fugerit.java.core.db.dao.DAOFactory#getConnectionFactory()
	 */
    @Override
	public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
    
    /* (non-Javadoc)
	 * @see org.fugerit.java.core.db.dao.DAOFactory#getFieldFactory()
	 */
    @Override
	public FieldFactory getFieldFactory() {
        return fieldFactory;
    }

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.dao.DAOFactory#getSqlArgs()
	 */
	@Override
	public Object[] getSqlArgs() {
		return sqlArgs;
	}

	public void setSqlArgs(Object[] sqlArgs) {
		this.sqlArgs = sqlArgs;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.dao.DAOFactory#getDaoUtils()
	 */
	@Override
	public DAOUtils getDaoUtils() {
		return daoUtils;
	}

	
}
