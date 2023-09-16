package org.fugerit.java.core.db.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.fugerit.java.core.cfg.CloseHelper;
import org.fugerit.java.core.io.helper.HelperIOException;
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
		HelperIOException.apply( () -> CloseHelper.closeAllAndThrowConfigRuntime( this.rs, this.stm ) );
	}

	@Override
	public boolean hasNext() throws DAOException {
		return DAOException.get( () -> {
			boolean res = this.rs.next();
			if ( res ) {
				count++;
			}
			return res;
		} );
	}

	@Override
	public T next() throws DAOException {
		return DAOException.get( () -> this.rse.extractNext( rs ) );
	}

	@Override
	public long getCount() {
		return this.count;
	}

	public static <T> LoadResultNG<T> newLoadResult( RSExtractor<T> rse, Statement stm, ResultSet rs ) {
		return new DefaultLoadResultNG<>( rse, stm, rs );
	}
	
	public static <T> LoadResultNG<T> newLoadResult( Connection conn, RSExtractor<T> rse, Statement stm, ResultSet rs ) {
		return new CloseConnectionLoadResultNG<>( rse, stm, rs, conn );
	}
	
	public static <T> LoadResultNG<T> newLoadResult( Connection conn, OpDAO<T> opDAO ) {
		return DAORuntimeException.get( () -> {
			PreparedStatement pstm = conn.prepareStatement( opDAO.getSql() );
			DAOHelper.setAll( pstm , opDAO.getFieldList(), logger );
			ResultSet rs = pstm.executeQuery();
			return new DefaultLoadResultNG<>( opDAO.getRsExtractor(), pstm, rs );	
		} );
	}
	
	public static <T> LoadResultNG<T> newLoadResultCloseConnection( Connection conn, OpDAO<T> opDAO ) {
		return DAORuntimeException.get( () -> {
			PreparedStatement pstm = conn.prepareStatement( opDAO.getSql() );
			DAOHelper.setAll( pstm , opDAO.getFieldList(), logger );
			ResultSet rs = pstm.executeQuery();
			return new CloseConnectionLoadResultNG<>( opDAO.getRsExtractor(), pstm, rs, conn );			
		} );
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
		HelperIOException.apply( () -> CloseHelper.closeAllAndThrowConfigRuntime( super::close, this.conn ) );
	}
	
}

