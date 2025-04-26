package test.org.fugerit.java.core.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.FieldFactory;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.dao.OpDAO;
import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.db.helpers.BlobData;
import org.fugerit.java.core.db.helpers.DAOID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;
import test.org.fugerit.java.core.db.TestBasicDBHelper;

@Slf4j
class TestFieldList extends TestBasicDBHelper {

	@BeforeAll
	static void initTest() {
		TestBasicDBHelper.init();
	}
	
	private static final String TEST_TABLE = "CREATE TABLE fugerit.test_field_list ("
			+ "	field01 BIGINT,"
			+ "	field02 BIGINT,"
			+ "	field03 BIGINT,"
			+ "	field04 BIGINT,"
			+ "	field05 BIGINT,"
			+ "	field06 BIGINT,"
			+ "	field07 BIGINT,"
			+ "	field08 VARCHAR(32),"
			+ "	field09 VARCHAR(32),"
			+ "	field10 TIMESTAMP,"
			+ "	field11 TIMESTAMP,"
			+ "	field12 TIMESTAMP,"
			+ "	field13 TIMESTAMP,"
			+ "	field14 BIGINT,"
			+ "	field15 blob,"
			+ "	field16 clob"
			+ ")";
	
	private static final String INSERT_SQL = " INSERT INTO fugerit.test_field_list "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	
	@Test
	void testFiledList() {
		DAORuntimeException.apply(() -> {
			BigDecimal value = BigDecimal.valueOf( 1 );
			FieldList fl = new FieldList();
			long time = System.currentTimeMillis();
			fl.addField( DAOID.valueOf( value.longValue() ) );
			fl.addField( value.byteValue() );
			fl.addField( value.intValue() );
			fl.addField( value.longValue() );
			fl.addField( value.longValue(), Types.BIGINT );
			fl.addField( value.floatValue() );
			fl.addField( value.doubleValue() );
			fl.addField( value.toString() );
			fl.addField( value.toString(), Types.VARCHAR );
			fl.addField( new Time( time ) );
			fl.addField( new Date( time ) );
			fl.addField( new Timestamp( time ) );
			fl.addField( new java.util.Date(), Types.TIMESTAMP );
			fl.addNullField( Types.INTEGER );
			fl.addField( ByteArrayDataHandler.newHandlerByte( "b".getBytes() ) );
			fl.addField( CharArrayDataHandler.newHandlerByte( "c".toCharArray() ) );
			Assertions.assertNotNull( new BasicTest().fullSerializationTest( fl ) );
			fl.newField( value.intValue(), Types.INTEGER );
			// print tests
			fl.getList().stream().forEach( c -> log.info( "current field {}", c ) );
			// set tests
			try ( Connection conn = TestBasicDBHelper.newConnection() ) {
				Assertions.assertFalse( DAOUtilsNG.execute( conn , OpDAO.newExecuteOp( TEST_TABLE ) ) );
				Assertions.assertNotEquals( 0 , DAOUtilsNG.update(conn, OpDAO.newExecuteOp( INSERT_SQL , fl ) ) );
			}
			// final tests field factory
			FieldFactory ff = new FieldFactory();
			ff.newField( (Object)ByteArrayDataHandler.newHandlerByte( "e".getBytes() ) );
			ff.newField( (Object)CharArrayDataHandler.newHandlerByte( "f".toCharArray() ) );
			ff.newField( (Object)DAOID.valueOf( value.longValue() ));
			ff.newField( (Object)DAOID.valueOf( value.longValue() ), Types.BIGINT );
			ff.newField( (Object)value.toString(), Types.VARCHAR );
			ff.newField( (Object)null, Types.BIGINT );
			ff.newField( (String)null, Types.VARCHAR );
			ff.newField( (DAOID)null);
			ff.newField( (Object)BlobData.valueOf( ByteArrayDataHandler.newHandlerByte( "f".getBytes() ) ), Types.BLOB );
		});
	}
	
}

/*

CREATE TABLE fugerit.test_field_list (
	field01 BIGINT,
	field02 BIGINT,
	field03 BIGINT,
	field04 BIGINT,
	field05 BIGINT,
	field06 BIGINT,
	field07 BIGINT,
	field08 VARCHAR(32),
	field09 VARCHAR(32),
	field10 TIMESTAMP,
	field11 TIMESTAMP,
	field12 TIMESTAMP,
	field13 TIMESTAMP,
	field14 BIGINT,
	field15 blob,
	field16 clob
)

*/