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
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.tool.ToolHandlerHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mttfranci
 *
 */
@Slf4j
public class ExportQuery extends ToolHandlerHelper {

	public static final String ARG_QUERY = "query";

	
	public static final String ARG_OUTPUT = "output";
	
	public static final String ARG_FORMAT = "format";
	public static final String ARG_FORMAT_DEFAULT = "html";
	
	private void openFile( PrintWriter pw, String format ) {
		log.trace( "format : {}", format );
		pw.println( "<table>" );
	}
	
	private void closeFile( PrintWriter pw, String format ) {
		log.trace( "format : {}", format );
		pw.println( "</table>" );
	}
	
	private void addRecord( PrintWriter pw, String[] currentRecord, boolean header ) {
		pw.println( "<tr>" );
		String openTag = "<td>";
		String closeTag = "</td>";
		if ( header ) {
			openTag = "<th>";
			closeTag = "</th>";
		}
		for ( int k=0; k<currentRecord.length; k++ ) {
			pw.print( openTag );
			if ( StringUtils.isEmpty( currentRecord[k] ) ) {
				pw.print( "&nbsp;" );	
			} else {
				pw.print( currentRecord[k] );
			}
			pw.println( closeTag );
		}
		pw.println( "</tr>" );
	}
	
	private ConnectionFactory getCf( Properties params, ClassLoader cl ) {
		return SafeFunction.get( () -> ConnectionFactoryImpl.newInstance( params, null, cl ) );
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.tool.ToolHandlerHelper#handleWorker(java.util.Properties)
	 */
	@Override
	public int handleWorker(Properties params) {
		ClassLoader cl = this.getClassLoader( params );
		ConnectionFactory cf = this.getCf(params, cl);
		String output = params.getProperty( ARG_OUTPUT );
		return SafeFunction.get( () -> {
			try (
					PrintWriter pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream( output ) ) );
						Connection conn = cf.getConnection();
						Statement stm = conn.createStatement() ) {
					String sql = params.getProperty( ARG_QUERY );
					String format = params.getProperty( ARG_FORMAT, ARG_FORMAT_DEFAULT );
					openFile( pw, format );
					try ( ResultSet rs = stm.executeQuery( sql ) ) {
						ResultSetMetaData rsmd = rs.getMetaData();
						String[] head = new String[rsmd.getColumnCount()];
						for ( int k=0; k<rsmd.getColumnCount(); k++ ) {
							head[k] = rsmd.getColumnLabel( k+1 );
						}
						addRecord( pw , head , true );
						while ( rs.next() ) {
							String[] localRecord = new String[rsmd.getColumnCount()];
							for ( int k=0; k<rsmd.getColumnCount(); k++ ) {
								Object obj = rs.getObject( k+1 );
								if ( obj == null ) {
									obj = "";
								}
								localRecord[k] = String.valueOf( obj );
							}
							addRecord( pw , localRecord , false );				
						}
						closeFile( pw, format );
					}
					pw.flush();
				}
			return EXIT_OK;
		} );
	}

}
