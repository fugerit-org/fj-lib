package org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fugerit.java.core.log.BasicLogObject;

/*
 * 
 *
 * @author Fugerit
 *
 */
public class BufferedDAO extends BasicLogObject {

	public static final int DEFAULT_COMMIT_ON = 1000;
	
	private BasicDAO basicDAO;
	
	private List updateList;
	
	private int commitOn;
	
	public BufferedDAO( BasicDAO basicDAO ) {
		this( basicDAO, DEFAULT_COMMIT_ON );
	}
	
	public BufferedDAO( BasicDAO basicDAO, int commitOn ) {
		this.basicDAO = basicDAO;
		this.updateList = new ArrayList();
		this.commitOn = commitOn;
	}
	
	/*
	 * <p>Executed a buffered update operation</p>
	 * 
	 * @param opDAO	The update operation to add to the buffer
	 * @return	<code>0</code> if the current operation is buffered, or <code>int</code> 
	 * 			representing the total result of the update operations if the BufferedDAO flushes the result 
	 * @throws DAOException		if troubles arises
	 */
	public int update( OpDAO opDAO ) throws DAOException {
		int result = 0;
		this.updateList.add( opDAO );
		if ( this.updateList.size()==this.commitOn ) {
			result = this.doUpdate( this.updateList );
		}
		return result;
	}
	
	public int close() throws DAOException {
		int result = this.doUpdate( this.updateList );
		this.updateList = null;
		this.basicDAO = null;
		return result;
	}
	
	private int doUpdate( List list ) throws DAOException {
		int result = 0;
		Connection conn = basicDAO.getConnection();
		try {
			conn.setAutoCommit( false );
			Iterator it = this.updateList.iterator();
			while ( it.hasNext() ) {
				OpDAO opDao = (OpDAO) it.next();
				String query = opDao.getSql();
				query = basicDAO.queryFormat( query, "BufferedDAO.doUpdate" );
				PreparedStatement ps = conn.prepareStatement( query );	
				basicDAO.setAll(ps, opDao.getFieldList() );
				result+= ps.executeUpdate();
				ps.close();
			}
			conn.setAutoCommit( true );
		} catch (SQLException e) {
			throw (new DAOException(e));
		} finally {
			basicDAO.close( conn );
			this.updateList.clear();
		}
		return result;
	}
	
	
}
