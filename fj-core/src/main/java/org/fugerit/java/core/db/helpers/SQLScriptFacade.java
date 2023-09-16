package org.fugerit.java.core.db.helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		return HelperIOException.get( () -> {
			Pattern regex = Pattern.compile("/\\*[^;(\\*/)]*?(;)[^;]*?\\*/", Pattern.DOTALL | Pattern.MULTILINE);
		    Matcher regexMatcher = regex.matcher( script );
		    List<String> list = new ArrayList<>();
		    while (regexMatcher.find()) {
		        String match = regexMatcher.group();
		        list.add( match );
		    } 
		    return list.toArray( new String[0] );	
		} );

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
