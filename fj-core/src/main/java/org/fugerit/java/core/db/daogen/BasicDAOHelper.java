package org.fugerit.java.core.db.daogen;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.DAOHelper;
import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.FieldFactory;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.log.LogObject;
import org.fugerit.java.core.util.checkpoint.CheckpointUtils;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>BasicDAOHelper for database operation.</p>
 * 
 * @author	fugerit
 *
 * @param <T>	the type returned by this DAOHelper
 * 
 */
@Slf4j
public class BasicDAOHelper<T> implements LogObject {
	
	@Override
	public Logger getLogger() {
		return log;
	}

	public static String fieldListToString( FieldList fl ) {
		StringBuilder buffer = new StringBuilder();
		buffer.append( "[" );
		if ( fl.size() > 0 ) {
			buffer.append( fl.getField( 0 ).toString() );
			for ( int k=1; k<fl.size(); k++ ) {
				buffer.append( "," );
				buffer.append( fl.getField( k ).toString() );
			}	
		}		
		buffer.append( "]" );
		return buffer.toString();
	}
	
	private DAOContext daoContext; 

	public BasicDAOHelper( DAOContext daoContext) {
		this.daoContext =daoContext;
	}
	
	public FieldList newFieldList() {
		return new FieldList( new FieldFactory() );
	}

	public T loadOneHelper(  SelectHelper query, RSExtractor<T> re ) throws DAOException {
		T res = null;
		List<T> l = new ArrayList<>();
		this.loadAllHelper(l, query, re);
		if ( !l.isEmpty() ) {
			res = l.get( 0 );
		}
		return res;
	}
	
	public void loadAllHelper( List<T> l, SelectHelper query, RSExtractor<T> re ) throws DAOException {
		this.loadAllHelper( l, query.getQueryContent(), query.getFields(), re );
	}
	

	public void loadAllHelper( List<T> l, String query, FieldList fields, RSExtractor<T> re ) throws DAOException {
		long startTime = System.currentTimeMillis();
		String queryId = DAOUtilsNG.createQueryId(startTime);
		log.debug( "queryId:'{}', loadAll START list    : '{}'", queryId, l.size() );
		log.debug( "queryId:'{}', loadAll sql           : '{}'", queryId, query );
		log.debug( "queryId:'{}', loadAll fields        : '{}'", queryId, fields.size() );
		log.debug( "queryId:'{}', loadAll RSExtractor   : '{}'", queryId, re);
		Connection conn = this.daoContext.getConnection();
		int i=0;
		try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
			DAOHelper.setAll( queryId, ps, fields , log );
			long executeStart = System.currentTimeMillis();
			try ( ResultSet rs = ps.executeQuery() ) {
				long executeEnd = System.currentTimeMillis();
				log.debug("queryId:'{}', loadAll query execute end time : '{}'", queryId, CheckpointUtils.formatTimeDiffMillis(executeStart, executeEnd ) );
				while (rs.next()) {
					l.add( re.extractNext( rs ) );
					i++;
				}
				log.debug("queryId:'{}', loadAll query result set end time : '{}'", queryId, CheckpointUtils.formatTimeDiffMillis( executeEnd, System.currentTimeMillis()) );
				log.debug("queryId:'{}', loadAll query total end time : '{}'", queryId, CheckpointUtils.formatTimeDiffMillis( startTime, System.currentTimeMillis()) );
			}
		} catch (SQLException e) {
			throw DAOException.convertEx( "Exception[query:"+query+",queryId:"+queryId+",record:"+i+"]", e );
		}
		log.debug("queryId:{}, loadAll END list : '{}'", queryId, l.size());
	}
	
	private int updateWorker( String queryId, FieldList fields, String query , long startTime) throws DAOException {
		int res = 0;
		Connection conn = this.daoContext.getConnection();
		try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
			DAOHelper.setAll( queryId, ps, fields , log );
			long executeStart = System.currentTimeMillis();
			res = ps.executeUpdate();
			log.debug("queryId:'{}', update query execute end time : '{}'", queryId, CheckpointUtils.formatTimeDiffMillis(executeStart, System.currentTimeMillis()) );
			log.debug("queryId:'{}', update total time : '{}'", queryId, CheckpointUtils.formatTimeDiffMillis(startTime, System.currentTimeMillis()) );
		} catch (SQLException e) {
			throw (new DAOException( e.getMessage()+"[query:"+query+",queryId:"+queryId+"]", e ));
		}
		return res;
	}
	
	public int update( QueryHelper queryHelper ) throws DAOException {
		int res = 0;
		try {
			String query = queryHelper.getQueryContent();
			FieldList fields = queryHelper.getFields();
			long startTime = System.currentTimeMillis();
			String queryId = DAOUtilsNG.createQueryId(startTime);
			log.debug( "queryId:'{}', update sql           : '{}'", queryId, query );
			log.debug( "queryId:'{}', update fields        : '{}'", queryId, fields.size() );
			this.updateWorker(queryId, fields, queryId, startTime);
			log.debug("update END res : '{}'", res );
		} catch (DAOException e) {
			throw new DAOException( e );
		}
		return res;
	}
	
	private String createSequenceQuery( String sequence ) {
		return " SELECT "+sequence+".NEXTVAL FROM DUAL";
	}
	
	public BigDecimal newSequenceValue( String sequence ) throws DAOException {
		BigDecimal id = null;
		String sql = this.createSequenceQuery(sequence);
		log.info( "newSequenceValue() sql > "+sql );
		try ( Statement stm = this.daoContext.getConnection().createStatement();
				ResultSet rs = stm.executeQuery( sql ) ) {
			if ( rs.next() ) {
				id = rs.getBigDecimal( 1 );
			}
		} catch (Exception e) {
			throw new DAOException( e );
		}
		return id;
	}
	
	public SelectHelper newSelectHelper( String queryView, String tableName ) {
		SelectHelper helper = null;
		if ( StringUtils.isNotEmpty( queryView ) ) {
			helper = new SelectHelper( tableName , this.newFieldList() );
			helper.appendToQuery( " SELECT * FROM ( "+queryView+" ) v " );	
		} else {
			helper = newSelectHelper( tableName );
		}
		return helper;
	}
	
	public SelectHelper newSelectHelper( String tableName ) {
		SelectHelper query = new SelectHelper( tableName , this.newFieldList() );
		query.initSelectEntity();
		return query;
	}
	
	public InsertHelper newInsertHelper( String tableName ) {
		InsertHelper query = new InsertHelper( tableName , this.newFieldList() );
		return query;
	}
	
	public UpdateHelper newUpdateHelper( String tableName ) {
		UpdateHelper query = new UpdateHelper( tableName , this.newFieldList() );
		return query;
	}
	
	public DeleteHelper newDeleteHelper( String tableName ) {
		DeleteHelper query = new DeleteHelper( tableName , this.newFieldList() );
		return query;
	}
	
	
}
