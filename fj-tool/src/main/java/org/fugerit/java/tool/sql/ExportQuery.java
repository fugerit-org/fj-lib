/**
 * 
 */
package org.fugerit.java.tool.sql;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.tool.ToolHandlerHelper;

/**
 * @author mttfranci
 *
 */
public class ExportQuery extends ToolHandlerHelper {

	public static final String ARG_QUERY = "query";

	
	public static final String ARG_OUTPUT = "output";
	
	public static final String ARG_FORMAT = "format";
	public static final String ARG_FORMAT_DEFAULT = "html";
	
	private void openFile( PrintWriter pw, String format ) throws Exception {
		pw.println( "<table>" );
	}
	
	private void closeFile( PrintWriter pw, String format ) throws Exception {
		pw.println( "</table>" );
	}
	
	private void addRecord( PrintWriter pw, String[] record, boolean header ) throws Exception {
		pw.println( "<tr>" );
		String openTag = "<td>";
		String closeTag = "</td>";
		if ( header ) {
			openTag = "<th>";
			closeTag = "</th>";
		}
		for ( int k=0; k<record.length; k++ ) {
			pw.print( openTag );
			if ( StringUtils.isEmpty( record[k] ) ) {
				pw.print( "&nbsp;" );	
			} else {
				pw.print( record[k] );
			}
			pw.println( closeTag );
		}
		pw.println( "</tr>" );
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.tool.ToolHandlerHelper#handleWorker(java.util.Properties)
	 */
	@Override
	public int handleWorker(Properties params) throws Exception {
		int exit = EXIT_KO_DEFAULT;
		ClassLoader cl = this.getClassLoader( params );
		ConnectionFactory cf = ConnectionFactoryImpl.newInstance( params, null, cl );
		
		String output = params.getProperty( ARG_OUTPUT );
		try (
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream( output ) ) );
				Connection conn = cf.getConnection();
				Statement stm = conn.createStatement() ) {
			String sql = params.getProperty( ARG_QUERY );
			String format = params.getProperty( ARG_FORMAT, ARG_FORMAT_DEFAULT );
			openFile( pw, format );
			try ( ResultSet rs = stm.executeQuery( sql ) ) {
				ResultSetMetaData rsmd = rs.getMetaData();
				String head[] = new String[rsmd.getColumnCount()];
				for ( int k=0; k<rsmd.getColumnCount(); k++ ) {
					head[k] = rsmd.getColumnLabel( k+1 );
				}
				addRecord( pw , head , true );
				while ( rs.next() ) {
					String record[] = new String[rsmd.getColumnCount()];
					for ( int k=0; k<rsmd.getColumnCount(); k++ ) {
						Object obj = rs.getObject( k+1 );
						if ( obj == null ) {
							obj = "";
						}
						record[k] = String.valueOf( obj );
					}
					addRecord( pw , record , false );				
				}
				closeFile( pw, format );
			}
			pw.flush();
			exit = EXIT_OK;
		} catch (Exception e) {
			logger.error( "Error", e );
		}
		return exit;
	}

}
