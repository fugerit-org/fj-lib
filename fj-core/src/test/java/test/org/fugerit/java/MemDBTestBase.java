package test.org.fugerit.java;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.helpers.SQLScriptFacade;
import org.fugerit.java.core.db.helpers.SQLScriptReader;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.PropsIO;
import org.junit.After;
import org.junit.Before;

public class MemDBTestBase extends BasicTest {

	public static final String DEFAULT_DB_CONN_PATH = "core/memdb/base-db-conn.properties";
	public static final String DEFAULT_DB_INIT_PATH = "core/memdb/base_db_init.sql";

	private Connection conn;

	public MemDBTestBase(ConnectionFactory cf) throws DAOException {
		this.conn = cf.getConnection();
	}

	public MemDBTestBase(String path) throws Exception {
		Properties props = PropsIO.loadFromClassLoader(path);
		ConnectionFactory cf = ConnectionFactoryImpl.newInstance(props);
		this.conn = cf.getConnection();
	}

	public MemDBTestBase() throws Exception {
		this(DEFAULT_DB_CONN_PATH);
	}

	public Connection getConnection() {
		return this.conn;
	}

    @Before
    public void create() throws Exception
    {
		try (InputStream is = ClassHelper.loadFromDefaultClassLoader(DEFAULT_DB_INIT_PATH);

				SQLScriptReader reader = new SQLScriptReader(is)) {
			int res = SQLScriptFacade.executeAll(reader, this.getConnection());
			logger.info("initDB() total exec script script : " + res);
		}
    }

    @After
    public void destroy() throws Exception {
		conn.close();
    }    

	@Override
	protected void finalize() throws Throwable {
		this.conn = null;
	}

}
