package test.org.fugerit.java.tool;

import org.fugerit.java.test.db.helper.MemTestDBHelper;
import org.fugerit.java.tool.Launcher;
import org.fugerit.java.tool.ToolHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestExportQuery {

	public static final String DEFAULT_DB_CONN_PATH = "test/memdb/base-db-conn.properties";
	public static final String DEFAULT_DB_INIT_PATH = "test/memdb/base_db_init.sql";
	
	@BeforeAll
	static void init() {
		MemTestDBHelper.init( DEFAULT_DB_CONN_PATH ,  DEFAULT_DB_INIT_PATH );
	}
	
	@Test
	void testExportQuery() throws Exception {
		int res = Launcher.handle( ArgHelper.readTestParams( "export-query" ) );
		Assertions.assertEquals( ToolHandler.EXIT_OK , res );
	}
	
	@Test
	void testExportQueryFail() throws Exception {
		int res = Launcher.handle( ArgHelper.readTestParams( "export-query-fail" ) );
		Assertions.assertNotEquals( ToolHandler.EXIT_OK , res );
	}
	
	
}
