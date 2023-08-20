package org.fugerit.java.core.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XMLClean {
	
	public static final String CLEAN_REGEX = "(?<![\\uD800-\\uDBFF])[\\uDC00-\\uDFFF]|[\\uD800-\\uDBFF](?![\\uDC00-\\uDFFF])|[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F-\\x9F\\uFEFF\\uFFFE\\uFFFF]"; 
	
	public static String cleanXML( String s ) {
		return s.replaceAll( CLEAN_REGEX , "" );
	}
	
	public static void cleanStream( Reader r, Writer w ) throws Exception {
		try ( BufferedReader reader = new BufferedReader( r );
				StringWriter sw = new StringWriter();
				PrintWriter writer = new PrintWriter( sw, false ) ) {
			String line = reader.readLine();
			while ( line != null ) {
				writer.println( line );
				line = reader.readLine();
			}
			w.write( sw.toString() );
			w.flush();
		}
	}
	
	public static void cleanFolder( File dir, String prefix ) throws Exception {
		File[] list = dir.listFiles();
		for ( int k = 0; k<list.length; k++ ) {
			File current = list[k];
			log.info( "PROCESSING : {}", current.getCanonicalPath() );
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
				try ( FileReader fr = new FileReader( new File( in ) );
						FileWriter fw = new FileWriter( new File( out ) ) ) {
					cleanStream( fr , fw );				
				}
			} else {
				cleanFolder( file, out );
			}
		} catch (Exception e) {
			log.error( "Error : "+e, e );
		}
	}
	
}
