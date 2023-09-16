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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fugerit.java.core.log.BasicLogObject;

public class LoadResult<T> extends BasicLogObject {

	private LoadResult(RSExtractor<T> re, FieldList fields, String query, BasicDAO<T> dao) {
		super();
		this.re = re;
		this.fields = fields;
		this.query = query;
		this.basicDAO = dao;
	}

	public static <T> LoadResult<T> initResult( BasicDAO<T> basicDAO, String query, FieldList fields, RSExtractor<T> re ) {
		return new LoadResult<>( re, fields, query, basicDAO );
	}
	
	private RSExtractor<T> re;
	
	private FieldList fields;
	
	private String query;
	
	private BasicDAO<T> basicDAO;
	
	private Connection conn;
	
	private PreparedStatement ps;
	
	private ResultSet rs;
	
	private String getSelectCount() {
		return "SELECT count(*) FROM ( "+this.query+" ) tmp";
	}
	
    public long startCount() throws DAOException {
    	long count = 0;
	    this.getLogger().debug("start START");
	    this.query = this.basicDAO.queryFormat( query, "LoadResult.startCount" );
	    this.getLogger().debug("start fields        : '{}'", fields.size());
        this.getLogger().debug("start RSExtractor   : '{}'", re);
        try ( PreparedStatement psCount = this.conn.prepareStatement( this.getSelectCount() )) {
        	this.basicDAO.setAll(psCount, fields);
        	try ( ResultSet rsCount = psCount.executeQuery() ) {
        		if ( rsCount.next() ) {
                	count = rsCount.getLong( 1 );
                }		
        	}
        } catch (SQLException e) {
            throw DAOException.convertEx( e );
        }
        this.start();
        this.getLogger().debug("start END" );
        return count;
    } 
	
    public void start() throws DAOException {
	    this.getLogger().debug("start START");
		query = this.basicDAO.queryFormat( query, "LoadResult.start" );
        this.getLogger().debug("start fields        : '{}'", fields.size());
        this.getLogger().debug("start RSExtractor   : '{}'", re);
        this.conn = this.basicDAO.getConnection();
        try {
            this.ps = conn.prepareStatement( query );
            this.basicDAO.setAll(ps, fields);
            this.rs = ps.executeQuery();
        } catch (SQLException e) {
            throw (new DAOException(e));
        }
        this.getLogger().debug("start END" );
    } 
    
    public boolean hasNext() throws DAOException {
    	boolean result = false;
    	try {
			result = this.rs.next();
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
		return result;
    }
    
    public T getNext() throws DAOException {
    	T result = null;
    	try {
			result = this.re.extractNext( rs );
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
		return result;
    }
    
    public void end() throws DAOException {
    	try {
    		this.rs.close();
    		this.ps.close();
			this.conn.close();
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
    }
    
	
}
