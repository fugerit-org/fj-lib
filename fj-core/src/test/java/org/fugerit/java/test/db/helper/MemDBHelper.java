package org.fugerit.java.test.db.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MemDBHelper {

	public static final String DEFAULT_DB_CONN_PATH = "test/memdb/base-db-conn.properties";
	public static final String DEFAULT_DB_INIT_PATH = "test/memdb/base_db_init.sql";

	private static final String DRV = "db-mode-dc-drv";
	private static final String URL = "db-mode-dc-url";
	private static final String USR = "db-mode-dc-usr";
	private static final String PWD = "db-mode-dc-pwd";
	

	private static Properties cf;
	
	private static Connection newConnection( Properties props ) throws Exception {
		Class.forName( props.getProperty( DRV ) );
		return DriverManager.getConnection( props.getProperty( URL ), props.getProperty( USR ), props.getProperty( PWD ) );
	}

    public static void init() throws Exception
    {
    	if ( cf == null ) {
    		try ( InputStream is = MemDBHelper.class.getClassLoader().getResourceAsStream( DEFAULT_DB_CONN_PATH ) ) {
    			Properties props = new Properties();
    			props.load( is );
            	try ( Connection conn = newConnection( props ) ) {
            		try ( SQLScriptReader reader = new SQLScriptReader( MemDBHelper.class.getClassLoader().getResourceAsStream( DEFAULT_DB_INIT_PATH ) ) ) {
            			executeAll(reader, conn);
            			cf = props;
            		}
        		}	
    		}
    	}
    } 
    
    public static Connection newConnection() throws Exception {
    	init();
    	return newConnection( cf );
    }
	
	private static int executeAll( SQLScriptReader reader, Connection conn ) throws SQLException, IOException {
		int res = 0;
		try ( Statement stm = conn.createStatement() ) {
			while ( reader.hasNext() ) {
				String sql = reader.next();
				if ( sql.trim().length() > 0 ) {
					stm.execute( sql );
					res++;	
				}
			}	
		}
		return res;
	}
	
}

class SQLScriptReader implements AutoCloseable {

	private BufferedReader reader;
	
	private String nextScript;
	
	private boolean skipSemicolon;
	
	public SQLScriptReader( InputStream is ) {
		this.reader = new BufferedReader( new InputStreamReader( is ) );
		this.skipSemicolon = false;
		this.nextScript = null;
	}

	@Override
	public void close() throws Exception {
		this.reader.close();
	}
	
	public boolean hasNext() throws IOException {
		this.nextScript = null;
		StringBuilder currentScript = new StringBuilder();
		char[] c = new char[1];
		int read = this.reader.read( c );
		while ( read > 0 && this.nextScript == null ) {
			char current = c[0];
			if ( current == '\'' ) {
				this.skipSemicolon = !this.skipSemicolon;
			}
			if ( current == ';' && !skipSemicolon ) {
				this.nextScript = currentScript.toString();
			} else {
				currentScript.append(current );
				read = this.reader.read( c );
			}
		}
		return (this.nextScript != null);
	}
	
	public String next() {
		return this.nextScript;
	}
	
}
