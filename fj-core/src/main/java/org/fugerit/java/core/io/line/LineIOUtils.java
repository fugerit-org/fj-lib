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

public class LineIOUtils {
		
	public static List<String> readLines( File file ) throws FileNotFoundException, IOException {
		return readLines( new FileReader( file ) );
	}
	
	public static List<String> readLines( InputStream is ) throws IOException {
		return readLines( new InputStreamReader( is ) );
	}
	
	public static List<String> readLines( Reader r ) throws IOException {
		List<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader( r );
		while ( reader.ready() ) {
			list.add( reader.readLine() );
		}
		r.close();
		return list;
	}
	
	public static String[] toLines( List<String> list ) {
		return list.toArray( new String[0] ); 
	}
	
    public static LineReader createLineReader(InputStream stream) {
        return createLineReader(new InputStreamReader(stream));
    }
    
    public static LineReader createLineReader(Reader reader) {
        return (new LineInputReader(reader));
    }
    
    public static LineWriter createLineWriter(OutputStream stream) {
        return createLineWriter(new OutputStreamWriter(stream));
    }
    
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
