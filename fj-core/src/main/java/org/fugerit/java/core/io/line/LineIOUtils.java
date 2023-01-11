/****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.core 

	Copyright (c) 2006 Morozko

	All rights reserved. This program and the accompanying materials
	are made available under the terms of the Apache License v2.0
	which accompanies this distribution, and is available at
	http://www.apache.org/licenses/
	(txt version : http://www.apache.org/licenses/LICENSE-2.0.txt
	html version : http://www.apache.org/licenses/LICENSE-2.0.html)

   This product includes software developed at
   The Apache Software Foundation (http://www.apache.org/).
</copyright>
*****************************************************************/
/*
 * @(#)LineIOUtils.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.io.line
 * @creation	: 23-dic-2004 12.26.14
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.io.line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.jvfs.JFile;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Libreria di funzioni per la manipolazione di Input e Output in formato di linee.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Collection of function for handling Input/Output in lines.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class LineIOUtils {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Legge tutte le righe presenti in un <code>JFile</code>.</jdl:text>
	 * 		<jdl:text lang='en'>Reads all lines found in a <code>JFile</code>.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param file			<jdl:section>
     * 						<jdl:text lang='it'>Il <code>JFile</code> da cui leggere.</jdl:text>
     * 						<jdl:text lang='en'>The <code>JFile</code> to read from.</jdl:text>  
     *					</jdl:section>
	 * @return			<jdl:section>
     * 						<jdl:text lang='it'>Una <code>java.util.List</code> contenente tutte le righe lette.</jdl:text>
     * 						<jdl:text lang='en'>A <code>java.util.List</code> containing all the lines red.</jdl:text>  
     *					</jdl:section>
	 * @throws IOException	<jdl:section>
	 * 							<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 							<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *						</jdl:section>
	 */			
	public static List<String> readLines( JFile file ) throws FileNotFoundException, IOException {
		return readLines( file.getReader() );
	}	
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Legge tutte le righe presenti in un <code>java.io.File</code>.</jdl:text>
	 * 		<jdl:text lang='en'>Reads all lines found in a <code>java.io.File</code>.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param file			<jdl:section>
     * 						<jdl:text lang='it'>Il <code>java.io.File</code> da cui leggere.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.io.File</code> to read from.</jdl:text>  
     *					</jdl:section>
	 * @return			<jdl:section>
     * 						<jdl:text lang='it'>Una <code>java.util.List</code> contenente tutte le righe lette.</jdl:text>
     * 						<jdl:text lang='en'>A <code>java.util.List</code> containing all the lines red.</jdl:text>  
     *					</jdl:section>
	 * @throws IOException	<jdl:section>
	 * 							<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 							<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *						</jdl:section>
	 */		
	public static List<String> readLines( File file ) throws FileNotFoundException, IOException {
		return readLines( new FileReader( file ) );
	}
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Legge tutte le righe presenti in un <code>java.io.InputStream</code>.</jdl:text>
	 * 		<jdl:text lang='en'>Reads all lines found in a <code>java.io.InputStream</code>.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param is			<jdl:section>
     * 						<jdl:text lang='it'>Il <code>java.io.InputStream</code> da cui leggere.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.io.InputStream</code> to read from.</jdl:text>  
     *					</jdl:section>
	 * @return			<jdl:section>
     * 						<jdl:text lang='it'>Una <code>java.util.List</code> contenente tutte le righe lette.</jdl:text>
     * 						<jdl:text lang='en'>A <code>java.util.List</code> containing all the lines red.</jdl:text>  
     *					</jdl:section>
	 * @throws IOException	<jdl:section>
	 * 							<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 							<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *						</jdl:section>
	 */	
	public static List<String> readLines( InputStream is ) throws IOException {
		return readLines( new InputStreamReader( is ) );
	}
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Legge tutte le righe presenti in un <code>java.io.Reader</code>.</jdl:text>
	 * 		<jdl:text lang='en'>Reads all lines found in a <code>java.io.Reader</code>.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param r			<jdl:section>
     * 						<jdl:text lang='it'>Il <code>java.io.Reader</code> da cui leggere.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.io.Reader</code> to read from.</jdl:text>  
     *					</jdl:section>
	 * @return			<jdl:section>
     * 						<jdl:text lang='it'>Una <code>java.util.List</code> contenente tutte le righe lette.</jdl:text>
     * 						<jdl:text lang='en'>A <code>java.util.List</code> containing all the lines red.</jdl:text>  
     *					</jdl:section>
	 * @throws IOException	<jdl:section>
	 * 							<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 							<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *						</jdl:section>
	 */
	public static List<String> readLines( Reader r ) throws IOException {
		List<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader( r );
		while ( reader.ready() ) {
			list.add( reader.readLine() );
		}
		r.close();
		return list;
	}
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Mette tutte li righe contenute in a <code>java.util.List</code> dentro un array di stringhe.</jdl:text>
	 * 		<jdl:text lang='en'>Puts all lines in a <code>java.util.List</code> in a string array.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param list		<jdl:section>
     * 						<jdl:text lang='it'>La <code>java.util.List</code> da cui leggere.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.util.List</code> to read from.</jdl:text>  
     *					</jdl:section>
	 * @return			<jdl:section>
     * 						<jdl:text lang='it'>L' array di stringhe contenente le righe.</jdl:text>
     * 						<jdl:text lang='en'>The string array containing the lines.</jdl:text>  
     *					</jdl:section>
	 */
	public static String[] toLines( List<String> list ) {
		return list.toArray( new String[0] ); 
	}
	
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea un nuovo <code>LineReader</code> a partire da un <code>java.io.InputStream</code>.</jdl:text>
     * 		<jdl:text lang='en'>Creates a new <code>LineReader</code> based upon a <code>java.io.InputStream</code>.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param stream	<jdl:section>
     * 						<jdl:text lang='it'>Il <code>java.io.InputStream</code> da incapsulare.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.io.InputStream</code> to wrap.</jdl:text>  
     *					</jdl:section>
     * @return			<jdl:section>
     * 						<jdl:text lang='it'>Il nuovo <code>LineReader</code>.</jdl:text>
     * 						<jdl:text lang='en'>The new <code>LineReader</code>.</jdl:text>  
     *					</jdl:section>
     */   	
    public static LineReader createLineReader(InputStream stream) {
        return createLineReader(new InputStreamReader(stream));
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea un nuovo <code>LineReader</code> a partire da un <code>java.io.Reader</code>.</jdl:text>
     * 		<jdl:text lang='en'>Creates a new <code>LineReader</code> based upon a <code>java.io.Reader</code>.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param reader	<jdl:section>
     * 						<jdl:text lang='it'>Il <code>java.io.Reader</code> da incapsulare.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.io.Reader</code> to wrap.</jdl:text>  
     *					</jdl:section>
     * @return			<jdl:section>
     * 						<jdl:text lang='it'>Il nuovo <code>LineReader</code>.</jdl:text>
     * 						<jdl:text lang='en'>The new <code>LineReader</code>.</jdl:text>  
     *					</jdl:section>
     */       
    public static LineReader createLineReader(Reader reader) {
        return (new LineInputReader(reader));
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea un nuovo <code>LineWriter</code> a partire da un <code>java.io.OutputStream</code>.</jdl:text>
     * 		<jdl:text lang='en'>Creates a new <code>LineWriter</code> based upon a <code>java.io.OutputStream</code>.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param stream	<jdl:section>
     * 						<jdl:text lang='it'>Il <code>java.io.OutputStream</code> da incapsulare.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.io.OutputStream</code> to wrap.</jdl:text>  
     *					</jdl:section>
     * @return			<jdl:section>
     * 						<jdl:text lang='it'>Il nuovo <code>LineWriter</code>.</jdl:text>
     * 						<jdl:text lang='en'>The new <code>LineWriter</code>.</jdl:text>  
     *					</jdl:section>
     */    
    public static LineWriter createLineWriter(OutputStream stream) {
        return createLineWriter(new OutputStreamWriter(stream));
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea un nuovo <code>LineWriter</code> a partire da un <code>java.io.Writer</code>.</jdl:text>
     * 		<jdl:text lang='en'>Creates a new <code>LineWriter</code> based upon a <code>java.io.Writer</code>.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param writer	<jdl:section>
     * 						<jdl:text lang='it'>Il <code>java.io.Writer</code> da incapsulare.</jdl:text>
     * 						<jdl:text lang='en'>The <code>java.io.Writer</code> to wrap.</jdl:text>  
     *					</jdl:section>
     * @return			<jdl:section>
     * 						<jdl:text lang='it'>Il nuovo <code>LineWriter</code>.</jdl:text>
     * 						<jdl:text lang='en'>The new <code>LineWriter</code>.</jdl:text>  
     *					</jdl:section>
     */
    public static LineWriter createLineWriter(Writer writer) {
        return (new LineOutputWriter(new PrintWriter(writer)));
    }
    
    // private constructor
    private LineIOUtils() {
        super();
    }

}

// implementation of LineWriter which uses an underlying PrintWriter
class LineOutputWriter implements LineWriter {
     
    private PrintWriter writer;
    
    public LineOutputWriter(PrintWriter writer) {
        this.writer = writer;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.io.line.LineWriter#println()
     */
    public void println() {
        this.writer.println();
        this.writer.flush();
    }
    /* (non-Javadoc)
     * @see org.morozko.java.core.io.line.LineWriter#println(java.lang.String)
     */
    public void println(String line) {
        this.writer.println(line);
        this.writer.flush();
    }
    /* (non-Javadoc)
     * @see org.morozko.java.core.io.line.LineWriter#print(java.lang.String)
     */
    public void print(String line) {
        this.writer.print(line);
        this.writer.flush();
    }    
    /* (non-Javadoc)
     * @see org.morozko.java.core.io.line.LineWriter#close()
     */
    public void close() throws IOException {
        this.writer.close();
    }   
}

// implementation of LineReader which uses an underlying BufferedReader
class LineInputReader implements LineReader {
    
    private BufferedReader reader;
    
    public LineInputReader(Reader reader) {
        if (reader instanceof BufferedReader) {
            this.reader = (BufferedReader)reader;
        } else {
            this.reader = new BufferedReader(reader);
        }
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.io.line.LineReader#readLine()
     */
    public String readLine() throws IOException {
        return this.reader.readLine();
    } 

    /* (non-Javadoc)
     * @see org.morozko.java.core.io.line.LineReader#close()
     */
    public void close() throws IOException {
        this.reader.close();
    }
    
}
