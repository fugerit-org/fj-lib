/*
 *		Fugerit Java Library is distributed under the terms of :
 *
 *                                Apache License
 *                          Version 2.0, January 2004
 *                       http://www.apache.org/licenses/
 *
 *
 *	Full license :
 *		http://www.apache.org/licenses/LICENSE-2.0
 *		
 *	Project site: 
 *		https://www.fugerit.org/
 *	
 *	SCM site :
 *		https://github.com/fugerit79/fj-lib	
 *
 */
package org.fugerit.java.core.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.fugerit.java.core.io.helper.StreamHelper;

/**
 * <p>StreamIO provides API for managing stream, reader an writer.</p>
 * 
 * @author fugerit
 *
 */
public class StreamIO {

	private StreamIO() {}
	
	/**
	 * Don't invoke close() method on source and destination at the end of operation.
	 */
    public static final int MODE_CLOSE_NONE = 0;
    
	/**
	 * Invoke close() method on both source and destination at the end of operation. 
	 */
    public static final int MODE_CLOSE_BOTH = 4;
    
	/**
	 * Invoke close() method on ource only at the end of operation. 
	 */       
    public static final int MODE_CLOSE_IN_ONLY = 1;
    
    
	/**
	 * Invoke close() method on destination only at the end of operation. 
	 */          
    public static final int MODE_CLOSE_OUT_ONLY = 2;
    
    
	/**
	 * Small size buffer.
	 */     
    public static final int BUFFERSIZE_LOW = 512;
    
	/**
	 * Medium size buffer.
	 */       
    public static final int BUFFERSIZE_MEDIUM = 1024;
    
	/**
	 * Big size buffer.
	 */      
    public static final int BUFFERSIZE_HIGH = 2048;
    
	/**
	 * Default size buffer.
	 * {@link StreamIO#BUFFERSIZE_MEDIUM}
	 */    
    public static final int BUFFERSIZE_DEFAULT = BUFFERSIZE_MEDIUM;
    
	/**
	 * No buffer at all (size = 1)
	 */  
    public static final int BUFFERSIZE_NOBUFFER = 1;	
	
	
	/**
	 * <p>Read an InputStream as a String.</p>
	 * 
	 * @param src		the input stream
	 * @return			the String
	 * @throws IOException	in case of troubles during the operation
	 */
	public static String readString( InputStream src ) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		pipeStream( src , baos, MODE_CLOSE_BOTH );
		return baos.toString();
	}	
	
	/**
	 * <p>Read an Reader as a String.</p>
	 * 
	 * @param src		the input stream
	 * @return			the String
	 * @throws IOException	in case of troubles during the operation
	 */
	public static String readString( Reader src ) throws IOException {
		StringWriter w = new StringWriter();
		pipeChar( src , w, MODE_CLOSE_BOTH);
		return w.toString();
	}	
	
	/**
	 * <p>Read an InputStream as a byte array.</p>
	 * 
	 * @param src		the input stream
	 * @return			the byte array
	 * @throws IOException	in case of troubles during the operation
	 */
	public static byte[] readBytes( InputStream src ) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		pipeStream( src , baos, MODE_CLOSE_BOTH);
		return baos.toByteArray();
	}
	
	/**
	 * <p>Pipe the content of a java.io.InputStream into a java.io.OutputStream.</p>
	 * 
	 * @param src				the source
	 * @param dst				the destination
	 * @param modeClose			the mode for closing source and/or destination
	 * @return					the number of char read/written
	 * @throws IOException		in case of troubles during the operation
	 */
    public static long pipeChar(Reader src, Writer dst, int modeClose) throws IOException {
        return StreamHelper.pipe(src, dst, BUFFERSIZE_DEFAULT, modeClose );
    }
	
    /**
	 * <p>Pipe the content of a java.io.InputStream into a java.io.OutputStream.</p>
	 * 
	 * @param src				the source
	 * @param dst				the destination
	 * @return					the number of char read/written
	 * @throws IOException		in case of troubles during the operation
	 */
    public static long pipeCharCloseBoth(Reader src, Writer dst) throws IOException {
        return pipeChar(src, dst, StreamIO.MODE_CLOSE_BOTH );
    }
    
    /**
     * <p>Pipe the content of a java.io.Reader into a java.io.Writer.</p>
     * 
	 * @param src				the source
	 * @param dst				the destination
     * @param modeClose			the mode for closing source and/or destination
     * @return					the number of byte read/written
     * @throws IOException		in case of troubles during the operation
     */
    public static long pipeStream(InputStream src, OutputStream dst, int modeClose) throws IOException {
    	  return StreamHelper.pipe(src, dst, BUFFERSIZE_DEFAULT, modeClose );
    }

    /**
     * <p>Pipe the content of a java.io.Reader into a java.io.Writer.</p>
     * 
	 * @param src				the source
	 * @param dst				the destination
     * @return					the number of byte read/written
     * @throws IOException		in case of troubles during the operation
     */
    public static long pipeStreamCloseBoth(InputStream src, OutputStream dst) throws IOException {
    	  return pipeStream(src, dst, StreamIO.MODE_CLOSE_BOTH);
    }
    
}
