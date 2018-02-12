package org.fugerit.java.core.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class XMLClean {

	public static final String CLEAN_REGEX = "(?<![\\uD800-\\uDBFF])[\\uDC00-\\uDFFF]|[\\uD800-\\uDBFF](?![\\uDC00-\\uDFFF])|[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F-\\x9F\\uFEFF\\uFFFE\\uFFFF]"; 
	
	public static String cleanXML( String s ) {
		return s.replaceAll( CLEAN_REGEX , "" );
	}
	
	public static void cleanStream( Reader r, Writer w ) throws Exception {
		BufferedReader reader = new BufferedReader( r );
		String line = reader.readLine();
		StringBuffer buffer = new StringBuffer();
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter( sw, false );
		int count = 0;
		while ( line != null ) {
			writer.println( line );
			line = reader.readLine();
			count++;
			if ( count>=100000 ) {
				w.write( sw.toString() );
				w.flush();
				writer.close();
				sw = new StringWriter();
				writer = new PrintWriter( sw, false );
			}
		}
		w.write( sw.toString() );
		writer.close();
		w.flush();
		w.close();
		reader.close();
	}
	
	public static void cleanFolder( File dir, String prefix ) throws Exception {
		File[] list = dir.listFiles();
		for ( int k = 0; k<list.length; k++ ) {
			File current = list[k];
			System.out.println( "PROCESSING : "+current.getCanonicalPath() );
			if ( current.isDirectory() ) {
				cleanFolder( current, prefix );
			} else {
				if ( current.getName().indexOf( ".xml" ) != -1 ) {
					File out = new File( dir, prefix+current.getName() );
					if ( !out.exists() ) {
						FileReader fr = new FileReader( current );
						FileWriter fw = new FileWriter( out );
						cleanStream( fr , fw );
					}
				}
 			}
		}
	} 
	
	public static void main( String[] args ) {
		try {
			String in = args[0];
			String out = args[1];
			File file = new File( in );
			if ( file.isFile() ) {
				FileReader fr = new FileReader( new File( in ) );
				FileWriter fw = new FileWriter( new File( out ) );
				cleanStream( fr , fw );				
			} else {
				cleanFolder( file, out );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
