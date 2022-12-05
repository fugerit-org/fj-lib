package org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUtilsNG {

	private final static Logger logger = LoggerFactory.getLogger( DAOUtilsNG.class );
	
	public static <T> LoadResultNG<T> extractAll( Connection conn, OpDAO<T> op ) throws Exception {
		return DefaultLoadResultNG.newLoadResult( conn, op );
	}
	
	public static <T> void extractAll( Connection conn, Collection<T> result, OpDAO<T> op ) throws Exception {
		try ( PreparedStatement pstm = conn.prepareStatement( op.getSql() ) ) {
			DAOHelper.setAll( pstm , op.getFieldList(), logger );
			try ( ResultSet rs = pstm.executeQuery() ) {
				while ( rs.next() ) {
					T current = op.getRsExtractor().extractNext( rs );
					result.add( current );
				}
			}
		}
	}
	
	public static <T> T extractOne( Connection conn, OpDAO<T> op ) throws Exception {
		T res = null;
		try ( PreparedStatement pstm = conn.prepareStatement( op.getSql() ) ) {
			DAOHelper.setAll( pstm , op.getFieldList(), logger );
			try ( ResultSet rs = pstm.executeQuery() ) {
				if ( rs.next() ) {
					res = op.getRsExtractor().extractNext( rs );
				}
			}
		}
		return res;
	}
	
	public static <T> T extraOne( Connection conn, String sql, RSExtractor<T> rse, Object... fields ) throws Exception {
		return extractOne( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> T extraOneFields( Connection conn, String sql, RSExtractor<T> rse, Field... fields ) throws Exception {
		return extractOne( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> LoadResultNG<T> extraAll( Connection conn,  String sql, RSExtractor<T> rse, Field... fields ) throws Exception {
		return extractAll( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> LoadResultNG<T> extraAllFields( Connection conn,  String sql, RSExtractor<T> rse, Object... fields ) throws Exception {
		return extractAll( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> void extraAll( Connection conn, Collection<T> result, String sql, RSExtractor<T> rse, Field... fields ) throws Exception {
		extractAll( conn, result, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> void extraAllFields( Connection conn, Collection<T> result, String sql, RSExtractor<T> rse, Object... fields ) throws Exception {
		extractAll( conn, result, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}

	public static <T> int update( Connection conn, OpDAO<T> op ) throws Exception {
		int res = 0;
		try ( PreparedStatement pstm = conn.prepareStatement( op.getSql() ) ) {
			DAOHelper.setAll( pstm , op.getFieldList(), logger );
			res = pstm.executeUpdate(); 
		}
		return res;
	}
	
	public static <T> int update( Connection conn, String sql, Object... fields ) throws Exception {
		return update(conn, OpDAO.newUpdateOp(sql, FieldList.newFieldList( fields ) ) );
	}
	public static <T> int updateFields( Connection conn, String sql, Field... fields ) throws Exception {
		return update(conn, OpDAO.newUpdateOp(sql, FieldList.newFieldList( fields ) ) );
	}
	
}
