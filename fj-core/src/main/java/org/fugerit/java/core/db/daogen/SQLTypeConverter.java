package org.fugerit.java.core.db.daogen;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class SQLTypeConverter {

	private SQLTypeConverter() {}
	
	public static Timestamp utilDateToSqlTimestamp(java.util.Date s) {
		Timestamp r = null;
		if ( s != null ) {
			r = new Timestamp( s.getTime() );
		}
		return r;
	}
	
	public static Date utilDateToSqlDate(java.util.Date s) {
		Date r = null;
		if ( s != null ) {
			r = new Date( s.getTime() );
		}
		return r;
	}
	
	public static Time utilDateToSqlTime(java.util.Date s) {
		Time r = null;
		if ( s != null ) {
			r = new Time( s.getTime() );
		}
		return r;
	}
	
	public static ByteArrayDataHandler blobToByteHandler( java.sql.Blob s ) throws SQLException {
		ByteArrayDataHandler r = null;
		if ( s != null ) {
			try {
				r = ByteArrayDataHandler.newHandlerDefault( s );
			} catch (Exception e) {
				throw new SQLException( e );
			}
		}
		return r;
	}
	
	public static CharArrayDataHandler clobToCharHandler( java.sql.Clob s ) throws SQLException {
		CharArrayDataHandler r = null;
		if ( s != null ) {
			try {
				r = CharArrayDataHandler.newHandlerDefault( s );
			} catch (Exception e) {
				throw new SQLException( e );
			}
		}
		return r;
	}
	
}
