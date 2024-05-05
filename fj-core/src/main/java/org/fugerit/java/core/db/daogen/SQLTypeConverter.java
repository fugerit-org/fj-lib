package org.fugerit.java.core.db.daogen;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.function.Function;

import org.fugerit.java.core.function.SafeFunction;

public class SQLTypeConverter {

	public static final Function<Exception, SQLException> CONVERT_EX = SQLException::new;
	
	private SQLTypeConverter() {}
	
	public static Timestamp utilDateToSqlTimestamp(java.util.Date s) {
		return SafeFunction.getIfNotNull( s , () -> new Timestamp( s.getTime() ) );
	}
	
	public static Date utilDateToSqlDate(java.util.Date s) {
		return SafeFunction.getIfNotNull( s , () -> new Date( s.getTime() ) );
	}
	
	public static Time utilDateToSqlTime(java.util.Date s) {
		return SafeFunction.getIfNotNull( s , () -> new Time( s.getTime() ) );
	}
	
	public static ByteArrayDataHandler blobToByteHandler( java.sql.Blob s ) throws SQLException {
		return SafeFunction.getEx( () -> ByteArrayDataHandler.newHandlerDefault( s ) , CONVERT_EX );
	}
	
	public static CharArrayDataHandler clobToCharHandler( java.sql.Clob s ) throws SQLException {
		return SafeFunction.getEx( () -> CharArrayDataHandler.newHandlerDefault( s ) , CONVERT_EX );
	}

	public static ZonedDateTime toZonedDateTime(java.util.Date dateToConvert) {
		return SafeFunction.getIfNotNull( dateToConvert , () ->
				new java.util.Date( dateToConvert.getTime() )
						.toInstant()
						.atZone(ZoneId.systemDefault()) );
	}

	public static LocalDate utilDateToLocalDate(java.util.Date dateToConvert) {
		return SafeFunction.getIfNotNull( dateToConvert , () -> toZonedDateTime( dateToConvert  ).toLocalDate() );
	}

	public static LocalDateTime utilDateToLocalDateTime(java.util.Date dateToConvert) {
		return SafeFunction.getIfNotNull( dateToConvert , () -> toZonedDateTime( dateToConvert  ).toLocalDateTime() );
	}

	public static LocalTime utilDateToLocalTime(Time dateToConvert) {
		return SafeFunction.getIfNotNull( dateToConvert , () -> toZonedDateTime( dateToConvert  ).toLocalTime() );
	}

	public static Date localDateToSqlDate(LocalDate dateToConvert) {
		return SafeFunction.getIfNotNull( dateToConvert , () -> java.sql.Date.valueOf(dateToConvert) );
	}

	public static Timestamp localDateTimeToSqlTimestamp(LocalDateTime dateToConvert) {
		return SafeFunction.getIfNotNull( dateToConvert , () -> java.sql.Timestamp.valueOf(dateToConvert) );
	}

	public static Time localDateTimeToSqlTime(LocalTime dateToConvert) {
		return SafeFunction.getIfNotNull( dateToConvert , () -> java.sql.Time.valueOf(dateToConvert) );
	}

}
