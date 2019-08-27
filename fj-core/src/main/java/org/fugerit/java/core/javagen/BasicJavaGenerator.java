package org.fugerit.java.core.javagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.TreeSet;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.io.FileIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic implementation of JavaGenerator
 * 
 * fix of importList (now keep order of elements)
 * 
 * @author Daneel
 *
 */
public abstract class BasicJavaGenerator implements JavaGenerator {

	protected static Logger logger = LoggerFactory.getLogger( BasicJavaGenerator.class );

	private StringWriter buffer;
	
	private PrintWriter writer;
	
	private String packageName;
	
	private String javaName;
	
	private File javaFile;
	
	private Collection<String> importList;
	
	public void init( File sourceFolder, String fullObjectBName ) throws ConfigException {
		this.buffer = new StringWriter();
		this.writer = new PrintWriter( this.buffer );
		int index = fullObjectBName.lastIndexOf( '.' );
		this.javaName = fullObjectBName.substring( index+1 );
		this.packageName = fullObjectBName.substring( 0, index );
		File packageFolder = new File( sourceFolder, packageName.replace( '.' , '/' ) );
		this.javaFile = new File( packageFolder, javaName+".java" );
		this.importList = new TreeSet<>();		
	}
	
	public PrintWriter getWriter() {
		return writer;
	}
	
	public String getContent() {
		return this.buffer.toString();
	}
	
	public String getPackageName() {
		return packageName;
	}

	public String getJavaName() {
		return javaName;
	}

	public File getJavaFile() {	
		return javaFile;
	}
	
	public Collection<String> getImportList() {
		return importList;
	}
	
	public void write() throws IOException {
		if ( !this.getJavaFile().getParentFile().exists() ) {
			logger.info( "Created dir : "+this.getJavaFile().getParentFile().getCanonicalPath()+" -> "+this.getJavaFile().getParentFile().mkdirs() );
		}
		FileIO.writeString( this.getContent() , this.getJavaFile() );
		logger.info( "Content written to : "+this.getJavaFile().getCanonicalPath() );
	}
	
	public abstract void generate() throws Exception;
	
	public static void customPartWorker( File file, PrintWriter writer, String startTag, String endTag, String indent ) throws FileNotFoundException, IOException {
		customPartWorker(file, writer, startTag, endTag, indent, "" );
	}
	
	public static void customPartWorker( File file, PrintWriter writer, String startTag, String endTag, String indent, String addIfEmpty ) throws FileNotFoundException, IOException {
		writer.println( indent+startTag );
		boolean customCode = false;
		boolean isEmpty = true;
		if ( file.exists() ) {
			try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) ) {
				String line = reader.readLine();
				while ( line != null ) {
					if ( line.contains( startTag ) ) {
						customCode = true;
					} else if ( line.contains( endTag ) ) {
						customCode = false;
					} else if ( customCode ) {
						writer.println( line );
						isEmpty = false;
					}
					line = reader.readLine();
				}
			}
		}
		if ( isEmpty ) {
			writer.print( addIfEmpty );
		}
		writer.println( indent+endTag );
		writer.println();
	}

	public void println() {
		writer.println();
	}
	
	public void println( String s ) {
		writer.println( s );
	}

}