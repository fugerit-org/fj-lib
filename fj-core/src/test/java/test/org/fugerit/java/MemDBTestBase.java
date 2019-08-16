package test.org.fugerit.java;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.helpers.SQLScriptFacade;
import org.fugerit.java.core.db.helpers.SQLScriptReader;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.PropsIO;
import org.junit.BeforeClass;

public class MemDBTestBase extends BasicTest {

	public static final String DEFAULT_DB_CONN_PATH = "core/memdb/base-db-conn.properties";
	public static final String DEFAULT_DB_INIT_PATH = "core/memdb/base_db_init.sql";

	private Connection conn;

	private static ConnectionFactory cf;
	
	public MemDBTestBase() {
		try {
			this.conn = cf.getConnection();
		} catch ( Exception e) {
			throw new RuntimeException( "Init error "+e, e );
		}
	}

	public Connection getConnection() {
		return this.conn;
	}

    @BeforeClass
    public static void create() throws Exception
    {
    	if ( cf == null ) {
        	Properties props = PropsIO.loadFromClassLoader( DEFAULT_DB_CONN_PATH );
    		cf = ConnectionFactoryImpl.newInstance(props);	
    		try (InputStream is = ClassHelper.loadFromDefaultClassLoader(DEFAULT_DB_INIT_PATH);
    				SQLScriptReader reader = new SQLScriptReader(is);
    				Connection conn = cf.getConnection() ) {
    			int res = SQLScriptFacade.executeAll(reader, conn);
    			logger.info("initDB() total exec script script : " + res);
    		}
    	}
    } 

	@Override
	protected void finalize() throws Throwable {

	}

	public void simpleTestSelectWorker( String select ) {
		try ( Connection conn = this.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery( select ) ) {
			int read = 0;
			while ( rs.next() ) {
				read++;
			}
			logger.info( "total record read from table : "+read );
		} catch (Exception e) {
			String message = "Error : "+e;
			logger.error( message, e );
			e.printStackTrace();
			fail( message );
		}
	}
	
}
