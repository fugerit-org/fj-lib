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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.fugerit.java.core.log.LogObject;

public class DAOHelper {

    public static void setAll( PreparedStatement ps, FieldList fields, LogObject log ) throws SQLException {
    	log.getLogger().debug( "Total Param Number : "+fields.size() );
    	int np = 0;
    	int k = 0;
		while ( k<fields.size() ) {
			np++;
			int param = (k+1);
			Field f = fields.getField(k);
			log.getLogger().debug( "Setting param n. "+param+", value: "+f.toString()+"(fl.size:"+fields.size()+")" );
			f.setField(ps, param);
			k++;
			log.getLogger().debug( "test : "+(k<fields.size())+" k:"+k+" fields.size:"+fields.size() );
		}    		
    	log.getLogger().debug( "Total param set : "+np );
    }

	public static String queryFormat( String sql, String method, LogObject log, DAOFactory bdf ) {
		log.getLogger().debug( "input  query : "+sql );
		log.getLogger().debug( "params :       "+bdf.getSqlArgs() );
		MessageFormat f = new MessageFormat( sql );
		sql = f.format( bdf.getSqlArgs() );
		log.getLogger().debug( "output query : "+sql );
		return sql;
	}
	
    public static void close(Connection conn) throws DAOException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        }
    }
    
	public static <T> void loadAll(List<T> l, String query, FieldList fields, RSExtractor<T> re,  DAOFactory bdf, LogObject log ) throws DAOException {
		log.getLogger().debug("loadAll START list : '"+l.size()+"'");
		query = DAOHelper.queryFormat( query, "loadAll", log, bdf );
		log.getLogger().debug("loadAll fields        : '"+fields.size()+"'");
		log.getLogger().debug("loadAll RSExtractor   : '"+re+"'");
		Connection conn = bdf.getConnection();
		int i=0;
		try {
			PreparedStatement ps = conn.prepareStatement( query );
			DAOHelper.setAll( ps, fields , log );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				l.add( re.extractNext( rs ) );
				i++;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw (new DAOException( e.getMessage()+"[query:"+query+",record:"+i+"]", e ));
		} finally {
			DAOHelper.close( conn );
		}
		log.getLogger().debug("loadAll END list : '"+l.size()+"'");
	}
    
    public static <T> T loadOne(String query, FieldList fields, RSExtractor<T> re, DAOFactory bdf, LogObject log ) throws DAOException {
	    log.getLogger().debug("loadOne START ");
		query = DAOHelper.queryFormat( query, "loadOne", log, bdf );
        log.getLogger().debug("loadOne fields        : '"+fields.size()+"'");
        log.getLogger().debug("loadOne RSExtractor   : '"+re+"'");    	
        T result = null;
        Connection conn = bdf.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            DAOHelper.setAll( ps, fields , log );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =  re.extractNext( rs );
            } 
            rs.close();
            ps.close();
        } catch (SQLException e) {
        	throw (new DAOException( e.getMessage()+"[query:"+query+"]", e ));
        } finally {
            DAOHelper.close( conn );
        }
        log.getLogger().debug("loadOne END : "+result );
        return result;
    }
	
}