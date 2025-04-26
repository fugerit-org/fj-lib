package test.org.fugerit.java.core.db;

import org.fugerit.java.test.db.helper.MemTestDBHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestBasicDBHelper extends MemTestDBHelper {

	public static final String DEFAULT_DB_CONN_PATH = "test/memdb/base-db-conn.properties";
	public static final String DEFAULT_DB_INIT_PATH = "test/memdb/base_db_init.sql";

	public static void init() {
		MemTestDBHelper.init( DEFAULT_DB_CONN_PATH ,  DEFAULT_DB_INIT_PATH );
	}

	@BeforeAll
	static void initTest() {
		init();
	}
	
	@Test
	void testFail() {
		Assertions.assertThrows( Exception.class , () -> { 
			MemTestDBHelper.initWorker( "fail-test", "script.sql" ); 
		} );
	}
	
	public static final String[] PRODUCT_NAME_STRING = { "maria", "mysql", "postgres", "oracle", "sqlserver" };
	
}
