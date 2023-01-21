package test.org.fugerit.java.core.db.helpers;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.test.db.helper.MemTestDBHelper;
import org.junit.BeforeClass;

public class MemDBHelper extends MemTestDBHelper {

	public static final String DEFAULT_DB_CONN_PATH = "test/memdb/base-db-conn.properties";
	public static final String DEFAULT_DB_INIT_PATH = "test/memdb/base_db_init.sql";
	
	@BeforeClass
	public static void init() {
    	try {
			MemTestDBHelper.init( DEFAULT_DB_CONN_PATH ,  DEFAULT_DB_INIT_PATH );
		} catch (Exception e) {
			throw new ConfigRuntimeException( "Mem db init error : "+e, e );
		}
	}

}
