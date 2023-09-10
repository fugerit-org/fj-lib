package org.fugerit.java.core.db.daogen;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.function.Function;

import org.fugerit.java.core.function.SafeFunction;

public class SQLTypeConverter {

	public static final Function<Exception, SQLException> CONVERT_EX = SQLException::new;
	
	private SQLTypeConverter() {}
	
	public static Timestamp utilDateToSqlTimestamp(java.util.Date s) {
		return SafeFunction.getIfNotNUll( s , () -> new Timestamp( s.getTime() ) );
	}
	
	public static Date utilDateToSqlDate(java.util.Date s) {
		return SafeFunction.getIfNotNUll( s , () -> new Date( s.getTime() ) );
	}
	
	public static Time utilDateToSqlTime(java.util.Date s) {
		return SafeFunction.getIfNotNUll( s , () -> new Time( s.getTime() ) );
	}
	
	public static ByteArrayDataHandler blobToByteHandler( java.sql.Blob s ) throws SQLException {
		return SafeFunction.getEx( () -> ByteArrayDataHandler.newHandlerDefault( s ) , CONVERT_EX );
	}
	
	public static CharArrayDataHandler clobToCharHandler( java.sql.Clob s ) throws SQLException {
		return SafeFunction.getEx( () -> CharArrayDataHandler.newHandlerDefault( s ) , CONVERT_EX );
	}
	
}
