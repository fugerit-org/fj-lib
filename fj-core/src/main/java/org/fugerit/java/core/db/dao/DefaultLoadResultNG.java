package org.fugerit.java.core.db.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.fugerit.java.core.cfg.CloseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLoadResultNG<T> implements LoadResultNG<T> {

	protected static final Logger logger = LoggerFactory.getLogger( DefaultLoadResultNG.class );
	
	private RSExtractor<T> rse;
	
	private Statement stm;
	
	private ResultSet rs;
	
	private long count;
	
	protected DefaultLoadResultNG(RSExtractor<T> rse, Statement stm, ResultSet rs) {
		super();
		this.rse = rse;
		this.stm = stm;
		this.rs = rs;
		this.count = 0;
	}

	@Override
	public void close() throws IOException { 
		Exception res = CloseHelper.closeAll( this.rs, this.stm );
		if ( res != null ) {
			throw new IOException( res );
		}
	}

	@Override
	public boolean hasNext() throws DAOException {
		boolean res = false;
		try {
			res = this.rs.next();
			if ( res ) {
				count++;
			}
		} catch (SQLException e) {
			throw new DAOException( e );
		}
		return res;
	}

	@Override
	public T next() throws DAOException {
		try {
			this.count++;
			return this.rse.extractNext( rs );
		} catch (SQLException e) {
			throw new DAOException( e ); 
		}
	}

	@Override
	public long getCount() {
		return this.count;
	}

	public static <T> LoadResultNG<T> newLoadResult( RSExtractor<T> rse, Statement stm, ResultSet rs ) throws DAOException {
		return new DefaultLoadResultNG<T>( rse, stm, rs );
	}
	
	public static <T> LoadResultNG<T> newLoadResult( Connection conn, RSExtractor<T> rse, Statement stm, ResultSet rs ) throws DAOException {
		return new CloseConnectionLoadResultNG<T>( rse, stm, rs, conn );
	}
	
	public static <T> LoadResultNG<T> newLoadResult( Connection conn, OpDAO<T> opDAO ) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement( opDAO.getSql() );
		DAOHelper.setAll( pstm , opDAO.getFieldList(), logger );
		ResultSet rs = pstm.executeQuery();
		LoadResultNG<T> lr = new DefaultLoadResultNG<T>( opDAO.getRsExtractor(), pstm, rs );
		return lr;
	}
	
	public static <T> LoadResultNG<T> newLoadResultCloseConnection( Connection conn, OpDAO<T> opDAO ) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement( opDAO.getSql() );
		DAOHelper.setAll( pstm , opDAO.getFieldList(), logger );
		ResultSet rs = pstm.executeQuery();
		LoadResultNG<T> lr = new CloseConnectionLoadResultNG<T>( opDAO.getRsExtractor(), pstm, rs, conn );
		return lr;
	}
	
}

class CloseConnectionLoadResultNG<T> extends DefaultLoadResultNG<T> {

	private Connection conn;
	
	public CloseConnectionLoadResultNG(RSExtractor<T> rse, Statement stm, ResultSet rs, Connection conn) {
		super(rse, stm, rs);
		this.conn  = conn;
	}

	@Override
	public void close() throws IOException {
		Exception res = CloseHelper.closeAll( () -> { super.close(); }, this.conn );
		if ( res != null ) {
			throw new IOException( res );
		}
	}
	
}

