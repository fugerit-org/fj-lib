package org.fugerit.java.core.db.dao;


import java.sql.Connection;

import org.fugerit.java.core.db.connect.ConnectionFactory;

/*
 * <p>.</p>
 *
 * Fugerit
 */
public class BasicDAOFactory {

	private DAOUtils daoUtils;
	
	public Object[] sqlArgs = new Object[0];
	
    public Connection getConnection() throws DAOException {
        return this.connectionFactory.getConnection();
    }
    
    private ConnectionFactory connectionFactory;
    
    private FieldFactory fieldFactory;
    
    /*
     * <p>Crea una nuova istanza di BasicDAOFactory.</p>
     * 
     * @param cFactory	the connection factory on which the DaoFactory is build
     */
    public BasicDAOFactory(ConnectionFactory cFactory) {
    	this( cFactory, new FieldFactory() );
    }    
    
    /*
     * <p>Crea una nuova istanza di BasicDAOFactory.</p>
     * 
     * @param cFactory	the connection factory on which the DaoFactory is build
     * @param fFactory	the field factory on which the DaoFactory is build
     */
    public BasicDAOFactory(ConnectionFactory cFactory, FieldFactory fFactory) {
        super();
        this.connectionFactory = cFactory;
        this.fieldFactory = fFactory;
        this.daoUtils = new DAOUtils( new GenericDAO( this ) );
    }

    /*
     * <p>Restituisce il valore di connectionFactory.</p>
     *
     * @return il valore di connectionFactory.
     */
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
    
    /*
     * <p>Restituisce il valore di fieldFactory.</p>
     *
     * @return il valore di fieldFactory.
     */
    public FieldFactory getFieldFactory() {
        return fieldFactory;
    }

	/*
	 * @return the sqlArgs
	 */
	public Object[] getSqlArgs() {
		return sqlArgs;
	}

	/*
	 * @param sqlArgs the sqlArgs to set
	 */
	public void setSqlArgs(Object[] sqlArgs) {
		this.sqlArgs = sqlArgs;
	}

	public DAOUtils getDaoUtils() {
		return daoUtils;
	}

	
}
