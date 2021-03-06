package org.fugerit.java.core.db.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLoadResultNG<T> implements LoadResultNG<T> {

	protected static final Logger logger = LoggerFactory.getLogger( DefaultLoadResultNG.class );
	
	private RSExtractor<T> rse;
	
	private PreparedStatement pstm;
	
	private ResultSet rs;
	
	private long count;
	
	protected DefaultLoadResultNG(RSExtractor<T> rse, PreparedStatement pstm, ResultSet rs) {
		super();
		this.rse = rse;
		this.pstm = pstm;
		this.rs = rs;
		this.count = 0;
	}

	@Override
	public void close() throws IOException {
		Exception res = null;
		try {
			this.rs.close();
		} catch (Exception e) {
			logger.error( "Errore closing result set : "+e, e );
			res = e;
		}
		try {
			this.pstm.close();
		} catch (Exception e) {
			logger.error( "Errore closing prepared statement : "+e, e );
			res = e;
		}
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

	public static <T> LoadResultNG<T> newLoadResult( Connection conn, OpDAO<T> opDAO ) throws Exception {
		PreparedStatement pstm = conn.prepareStatement( opDAO.getSql() );
		DAOHelper.setAll( pstm , opDAO.getFieldList(), logger );
		ResultSet rs = pstm.executeQuery();
		LoadResultNG<T> lr = new DefaultLoadResultNG<T>( opDAO.getRsExtractor(), pstm, rs );
		return lr;
	}
	
	public static <T> LoadResultNG<T> newLoadResultCloseConnection( Connection conn, OpDAO<T> opDAO ) throws Exception {
		PreparedStatement pstm = conn.prepareStatement( opDAO.getSql() );
		DAOHelper.setAll( pstm , opDAO.getFieldList(), logger );
		ResultSet rs = pstm.executeQuery();
		LoadResultNG<T> lr = new CloseConnectionLoadResultNG<T>( opDAO.getRsExtractor(), pstm, rs, conn );
		return lr;
	}
	
}

class CloseConnectionLoadResultNG<T> extends DefaultLoadResultNG<T> {

	private Connection conn;
	
	public CloseConnectionLoadResultNG(RSExtractor<T> rse, PreparedStatement pstm, ResultSet rs, Connection conn) {
		super(rse, pstm, rs);
		this.conn  = conn;
	}

	@Override
	public void close() throws IOException {
		Exception res = null;
		try {
			super.close();
		} catch (Exception e) {
			logger.error( "Errore closing resources : "+e, e );
			res = e;
		}
		try {
			this.conn.close();
		} catch (Exception e) {
			logger.error( "Errore closing connection : "+e, e );
			res = e;
		}
		if ( res != null ) {
			throw new IOException( res );
		}
	}
	
}

