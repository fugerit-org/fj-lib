package test.org.fugerit.java.tool;

import org.fugerit.java.test.db.helper.MemTestDBHelper;
import org.fugerit.java.tool.Launcher;
import org.fugerit.java.tool.ToolHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestExportQuery {

	public static final String DEFAULT_DB_CONN_PATH = "test/memdb/base-db-conn.properties";
	public static final String DEFAULT_DB_INIT_PATH = "test/memdb/base_db_init.sql";
	
	@BeforeClass
	public static void init() {
		MemTestDBHelper.init( DEFAULT_DB_CONN_PATH ,  DEFAULT_DB_INIT_PATH );
	}
	
	@Test
	public void testExportQuery() throws Exception {
		int res = Launcher.handle( ArgHelper.readTestParams( "export-query" ) );
		Assert.assertEquals( ToolHandler.EXIT_OK , res );
	}
	
	@Test
	public void testExportQueryFail() throws Exception {
		int res = Launcher.handle( ArgHelper.readTestParams( "export-query-fail" ) );
		Assert.assertNotEquals( ToolHandler.EXIT_OK , res );
	}
	
	
}
