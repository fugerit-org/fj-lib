package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
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
import org.fugerit.java.core.db.dao.FieldFactory;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.log.LogObject;
import org.fugerit.java.core.util.checkpoint.CheckpointUtils;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * @author	fugerit
 *
 * @param <T>	the type returned by this DAOHelper
 */
@Slf4j
public class BasicDAOHelper<T> implements Serializable, LogObject {
	
	@Override
	public Logger getLogger() {
		return log;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2430439741412903230L;

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
	
	protected String createQueryId( long startTime ) {
		return String.format( "%s_%s", Thread.currentThread().getId(), startTime );
	}

	public void loadAllHelper( List<T> l, String query, FieldList fields, RSExtractor<T> re ) throws DAOException {
		try {
			long startTime = System.currentTimeMillis();
			String queryId = this.createQueryId(startTime);
			log.debug( "queryId:{}, loadAll START list    : '{}' ", queryId, l.size() );
			log.debug( "queryId:{}, loadAll fields        : '{}'", queryId, fields.size() );
			log.debug( "queryId:{}, loadAll RSExtractor   : '{}'", queryId, re);
			Connection conn = this.daoContext.getConnection();
			int i=0;
			try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
				DAOHelper.setAll( ps, fields , this );
				try ( ResultSet rs = ps.executeQuery() ) {
					log.debug("queryId:{}, loadAll query execute end time : '{}'", queryId, CheckpointUtils.formatTimeDiff(startTime, System.currentTimeMillis()) );
					while (rs.next()) {
						l.add( re.extractNext( rs ) );
						i++;
					}
					log.debug("queryId:{}, loadAll query result end time : '{}'", queryId, CheckpointUtils.formatTimeDiff(startTime, System.currentTimeMillis()) );
				}
			} catch (SQLException e) {
				throw (new DAOException( e.getMessage()+"[query:"+query+",record:"+i+"]", e ));
			}
			log.debug("queryId:{}, loadAll END list : '{}'", queryId, l.size());
		} catch (DAOException e) {
			throw new DAOException( e );
		}
	}
	
	public int update( QueryHelper queryHelper ) throws DAOException {
		int res = 0;
		try {
			String query = queryHelper.getQueryContent();
			FieldList fields = queryHelper.getFields();
			log.debug( "update START list : '{}' ", query );
			log.debug( "update fields        : '{}'", fields.size() );
			Connection conn = this.daoContext.getConnection();
			int i=0;
			try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
				DAOHelper.setAll( ps, fields , this );
				res = ps.executeUpdate();
			} catch (SQLException e) {
				throw (new DAOException( e.getMessage()+"[query:"+query+",record:"+i+"]", e ));
			}
			log.debug("update END res : '{}'", res );
		} catch (DAOException e) {
			throw new DAOException( e );
		}
		return res;
	}
	
	public BigDecimal newSequenceValue( String sequence ) throws DAOException {
		BigDecimal id = null;
		String sql = " SELECT "+sequence+".NEXTVAL FROM DUAL";
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
