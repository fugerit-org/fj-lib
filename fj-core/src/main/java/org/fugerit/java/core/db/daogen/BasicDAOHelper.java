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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author	fugerit
 *
 * @param <T>	the type returned by this DAOHelper
 */
public class BasicDAOHelper<T> implements Serializable, LogObject {

	protected static Logger logger = LoggerFactory.getLogger( BasicDAOHelper.class );
	
	@Override
	public Logger getLogger() {
		return logger;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2430439741412903230L;

	public static String fieldListToString( FieldList fl ) {
		StringBuffer buffer = new StringBuffer();
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
		try {
			logger.debug( "loadAll START list : '{}' ", l.size() );
			logger.debug( "loadAll fields        : '{}'", fields.size() );
			logger.debug( "loadAll RSExtractor   : '{}'", re);
			Connection conn = this.daoContext.getConnection();
			int i=0;
			try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
				DAOHelper.setAll( ps, fields , this );
				try ( ResultSet rs = ps.executeQuery() ) {
					while (rs.next()) {
						l.add( re.extractNext( rs ) );
						i++;
					}
				}
			} catch (SQLException e) {
				throw (new DAOException( e.getMessage()+"[query:"+query+",record:"+i+"]", e ));
			}
			logger.debug("loadAll END list : '{}'", l.size());
		} catch (DAOException e) {
			throw new DAOException( e );
		}
	}
	

	public int update( QueryHelper queryHelper ) throws DAOException {
		int res = 0;
		try {
			String query = queryHelper.getQueryContent();
			FieldList fields = queryHelper.getFields();
			logger.debug( "update START list : '{}' ", query );
			logger.debug( "update fields        : '{}'", fields.size() );
			Connection conn = this.daoContext.getConnection();
			int i=0;
			try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
				DAOHelper.setAll( ps, fields , this );
				res = ps.executeUpdate();
			} catch (SQLException e) {
				throw (new DAOException( e.getMessage()+"[query:"+query+",record:"+i+"]", e ));
			}
			logger.debug("update END res : '{}'", res );
		} catch (DAOException e) {
			throw new DAOException( e );
		}
		return res;
	}
	
	public BigDecimal newSequenceValue( String sequence ) throws DAOException {
		BigDecimal id = null;
		String sql = " SELECT "+sequence+".NEXTVAL FROM DUAL";
		logger.info( "newSequenceValue() sql > "+sql );
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
