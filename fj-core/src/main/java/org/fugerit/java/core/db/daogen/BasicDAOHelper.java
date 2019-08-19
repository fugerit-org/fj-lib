package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.DAOHelper;
import org.fugerit.java.core.db.dao.FieldFactory;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.log.BasicLogObject;

public class BasicDAOHelper<T> extends BasicLogObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2430439741412903230L;

	private final static long LOG_TIME_THRESHOLD = TimeUnit.SECONDS.toMillis( 10 );

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

	public void loadAllHelper( List<T> l, SelectHelper query, RSExtractor<T> re ) throws DAOException {
		this.loadAllHelper( l, query.getQuery().toString(), query.getFields(), re );
	}
	

	public void loadAllHelper( List<T> l, String query, FieldList fields, RSExtractor<T> re ) throws DAOException {
		try {
			BasicDAOHelper<T> log = this;
			log.getLogger().debug( "loadAll START list : '{}' ", l.size() );
			log.getLogger().debug( "loadAll fields        : '{}'", fields.size() );
			log.getLogger().debug( "loadAll RSExtractor   : '{}'", re);
			long startTime = System.currentTimeMillis();
			Connection conn = this.daoContext.getConnection();
			long step1 = System.currentTimeMillis()-startTime;
			int i=0;
			try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
				long step2 = System.currentTimeMillis()-startTime;
				DAOHelper.setAll( ps, fields , log );
				long step3 = System.currentTimeMillis()-startTime;
				try ( ResultSet rs = ps.executeQuery() ) {
					long step4 = System.currentTimeMillis()-startTime;
					while (rs.next()) {
						l.add( re.extractNext( rs ) );
						i++;
					}
					long elapsed = System.currentTimeMillis()-startTime;
					if ( elapsed > LOG_TIME_THRESHOLD ) {
						String message = "BasicDAOHelper LOG_TIME_THRESHOLD="+LOG_TIME_THRESHOLD+", elapsed:"+elapsed+", rsCount:"+i;
						message+= " step1:"+step1+" step2:"+step2+" step3:"+step3+" step4:"+step4;
						log.getLogger().info( message );
					} 
				}
			} catch (SQLException e) {
				throw (new DAOException( e.getMessage()+"[query:"+query+",record:"+i+"]", e ));
			}
			log.getLogger().debug("loadAll END list : '{}'", l.size());
		} catch (DAOException e) {
			throw new DAOException( e );
		}
	}
	

	public int update( QueryHelper queryHelper ) throws DAOException {
		int res = 0;
		try {
			BasicDAOHelper<T> log = this;
			String query = queryHelper.getQueryContent();
			FieldList fields = queryHelper.getFields();
			log.getLogger().debug( "update START list : '{}' ", query );
			log.getLogger().debug( "update fields        : '{}'", fields.size() );
			Connection conn = this.daoContext.getConnection();
			int i=0;
			try ( PreparedStatement ps = conn.prepareStatement( query ) ) {
				DAOHelper.setAll( ps, fields , log );
				res = ps.executeUpdate();
			} catch (SQLException e) {
				throw (new DAOException( e.getMessage()+"[query:"+query+",record:"+i+"]", e ));
			}
			log.getLogger().debug("update END res : '{}'", res );
		} catch (DAOException e) {
			throw new DAOException( e );
		}
		return res;
	}
	
	public BigDecimal newSequenceValue( String sequence ) throws DAOException {
		BigDecimal id = null;
		String sql = " SELECT "+sequence+".NEXTVAL FROM DUAL";
		this.getLogger().info( "newSequenceValue() sql > "+sql );
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
