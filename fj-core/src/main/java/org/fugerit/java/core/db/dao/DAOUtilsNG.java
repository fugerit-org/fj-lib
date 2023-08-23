package org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import org.fugerit.java.core.util.checkpoint.CheckpointUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DAOUtilsNG {
	
	private DAOUtilsNG() {}
	
	public static String createQueryId( long startTime ) {
		return String.format( "%s_%s", Thread.currentThread().getId(), startTime );
	}
	
	public static <T> LoadResultNG<T> extractAll( Connection conn, OpDAO<T> op ) {
		return DefaultLoadResultNG.newLoadResult( conn, op );
	}
	
	public static <T> void extractAll( Connection conn, Collection<T> result, OpDAO<T> op ) {
		long startTime = System.currentTimeMillis();
		String queryId = createQueryId( startTime );
		log.debug( "queryId:'{}', extractAll sql           : '{}'", queryId, op.getSql() );
		if ( op.getFieldList() != null ) {
			log.debug( "queryId:'{}', extractAll fields        : '{}'", queryId, op.getFieldList().size() );
		}
		log.debug( "queryId:'{}', extractAll RSExtractor   : '{}'", queryId, op.getRsExtractor() );
		try ( PreparedStatement pstm = conn.prepareStatement( op.getSql() ) ) {
			DAOHelper.setAll( queryId, pstm , op.getFieldList(), log );
			try ( ResultSet rs = pstm.executeQuery() ) {
				log.debug("queryId:'{}', extractAll query execute end time : '{}'", queryId, CheckpointUtils.formatTimeDiff(startTime, System.currentTimeMillis()) );
				while ( rs.next() ) {
					T current = op.getRsExtractor().extractNext( rs );
					result.add( current );
				}
				log.debug("queryId:'{}', extractAll query result end time : '{}'", queryId, CheckpointUtils.formatTimeDiff(startTime, System.currentTimeMillis()) );
			}
		} catch (Exception e) {
			throw DAORuntimeException.convertExMethod( "extractAll" , e );
		}
	}
	
	public static <T> T extractOne( Connection conn, OpDAO<T> op ) {
		long startTime = System.currentTimeMillis();
		String queryId = createQueryId( startTime );
		log.debug( "queryId:'{}', extractOne sql           : '{}'", queryId, op.getSql() );
		if ( op.getFieldList() != null ) {
			log.debug( "queryId:'{}', extractOne fields        : '{}'", queryId, op.getFieldList().size() );
		}
		log.debug( "queryId:'{}', extractOne RSExtractor   : '{}'", queryId, op.getRsExtractor() );
		T res = null;
		try ( PreparedStatement pstm = conn.prepareStatement( op.getSql() ) ) {
			DAOHelper.setAll( queryId, pstm , op.getFieldList(), log );
			try ( ResultSet rs = pstm.executeQuery() ) {
				log.debug("queryId:'{}', extractOne query execute end time : '{}'", queryId, CheckpointUtils.formatTimeDiff(startTime, System.currentTimeMillis()) );
				if ( rs.next() ) {
					res = op.getRsExtractor().extractNext( rs );
				}
				log.debug("queryId:'{}', extractOne query result end time : '{}'", queryId, CheckpointUtils.formatTimeDiff(startTime, System.currentTimeMillis()) );
			}
		} catch (Exception e) {
			throw DAORuntimeException.convertExMethod( "extractOne" , e );
		}
		return res;
	}
	
	public static <T> int update( Connection conn, OpDAO<T> op ) {
		long startTime = System.currentTimeMillis();
		String queryId = createQueryId( startTime );
		log.debug( "queryId:'{}', update sql           : '{}'", queryId, op.getSql() );
		if ( op.getFieldList() != null ) {
			log.debug( "queryId:'{}', update fields        : '{}'", queryId, op.getFieldList().size() );
		}
		log.debug( "queryId:'{}', update RSExtractor   : '{}'", queryId, op.getRsExtractor() );
		int res = 0;
		try ( PreparedStatement pstm = conn.prepareStatement( op.getSql() ) ) {
			DAOHelper.setAll( queryId, pstm , op.getFieldList(), log );
			res = pstm.executeUpdate(); 
			log.debug("queryId:'{}', update query execute end time : '{}'", queryId, CheckpointUtils.formatTimeDiff(startTime, System.currentTimeMillis()) );
		} catch (Exception e) {
			throw DAORuntimeException.convertExMethod( "update" , e );
		}
		return res;
	}
	
	
	public static <T> T extraOne( Connection conn, String sql, RSExtractor<T> rse, Object... fields ) {
		return extractOne( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> T extraOneFields( Connection conn, String sql, RSExtractor<T> rse, Field... fields ) {
		return extractOne( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> LoadResultNG<T> extraAll( Connection conn,  String sql, RSExtractor<T> rse, Field... fields ) {
		return extractAll( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> LoadResultNG<T> extraAllFields( Connection conn,  String sql, RSExtractor<T> rse, Object... fields ) {
		return extractAll( conn, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> void extraAll( Connection conn, Collection<T> result, String sql, RSExtractor<T> rse, Field... fields ) {
		extractAll( conn, result, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}
	
	public static <T> void extraAllFields( Connection conn, Collection<T> result, String sql, RSExtractor<T> rse, Object... fields ) {
		extractAll( conn, result, OpDAO.newQueryOp(sql, FieldList.newFieldList( fields ), rse) );
	}

	public static int update( Connection conn, String sql, Object... fields ) {
		return update(conn, OpDAO.newUpdateOp(sql, FieldList.newFieldList( fields ) ) );
	}
	public static int updateFields( Connection conn, String sql, Field... fields ) {
		return update(conn, OpDAO.newUpdateOp(sql, FieldList.newFieldList( fields ) ) );
	}
	
}
