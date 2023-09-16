package org.fugerit.java.core.db.helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.fugerit.java.core.io.helper.HelperIOException;

public class SQLScriptFacade {
	
	private SQLScriptFacade() {}
	
	public static String[] parseScript( String script ) throws IOException {
		return parseSqlCommands(removeSqlComments(script));
	}

	public static String removeSqlComments( String script ) throws IOException {
		return HelperIOException.get( () -> script.replaceAll("\\B--*\\B.*", "") );
	}

	public static String[] parseSqlCommands( String script ) throws IOException {	
		return HelperIOException.get( () -> script.split( "(?<=;)\\s+" ) );

	}
	
	public static int executeAll( SQLScriptReader reader, Connection conn ) throws SQLException, IOException {
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
