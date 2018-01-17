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
 *		http://www.fugerit.org/java/
 *	
 *	SCM site :
 *		https://github.com/fugerit79/fj-lib	
 *
 */
package org.fugerit.java.core.io.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.compare.ComparePrimitiveFacade;
import org.fugerit.java.core.lang.helpers.ClassHelper;

/**
 * 
 * <p>This class provides primitives for piping streams and readers/writers content.</p>
 * 
 * @author Fugerit
 *
 */
public class StreamHelper {


	public static final String PATH_CLASSLOADER = "cl://";
	
	public static final String PATH_JNDI = "jndi://";
	
	public static final String PATH_FILE = "file://";
	
	public static InputStream resolveStream( String path ) throws Exception {
		return resolveStream( path, null );
	}
	
	public static InputStream resolveStream( String path, String basePath ) throws Exception {
		return resolveStream( path, basePath, StreamIO.class );
	}
	
	/*
	 * <p>Get a resource as stream from default class loader or from a <code>Class</code>
	 * 
	 * @param path		path of the resource to load as stream
	 * @param c			class to use as alternate class loader
	 * @param <T> 		The type of elements used by this method.
	 * @return			the resource in stream format
	 * @throws Exception	if something goes wrong during loading
	 */
	public static <T> InputStream getResourceStream( String path, Class<T> c ) throws Exception {
		InputStream is = ClassHelper.getDefaultClassLoader().getResourceAsStream( path );
		if ( is == null && c != null ) {
			is = c.getResourceAsStream( path );
		}
		return is;
	}
	
	public static InputStream resolveStream( String path, String basePath, Class<?> c ) throws Exception {	
		InputStream is = null;
		if ( path.indexOf( PATH_CLASSLOADER ) == 0 ) {
			// class loader
			path = path.substring( PATH_CLASSLOADER.length() );
			is = getResourceStream( path, c );
		} else {
			// default : file
			if ( path.indexOf( PATH_FILE ) == 0 ) {
				path = path.substring( PATH_FILE.length() );
			}
			File f = new File( path );
			if ( !f.exists() ) {
				f = new File( basePath, path );
			}
			if ( !f.exists() ) {
				throw ( new FileNotFoundException( f.getAbsolutePath() ) );
			} else {
				is = new FileInputStream( f );
			}
		}
		return is;
	}	
	
	/**
	 * <p>Check if the source should be closed.</p>
	 * 
	 * @param modeClose		the mode for closing source
	 * @return				<code>true</code> if the source should be closed, <code>false</code> otherwise.
	 * @see org.fugerit.java.core.io.StreamIO 
	 */
	public static boolean checkCloseInput( int modeClose ) {
		return ComparePrimitiveFacade.compareInt( modeClose , StreamIO.MODE_CLOSE_BOTH, StreamIO.MODE_CLOSE_IN_ONLY );
	}
	
	/**
	 * <p>Check if the destination should be closed.</p>
	 * 
	 * @param modeClose		the mode for closing destination
	 * @return				<code>true</code> if the destination should be closed, <code>false</code> otherwise.
	 * @see org.fugerit.java.core.io.StreamIO 
	 */
	public static boolean checkCloseOutput( int modeClose ) {
		return ComparePrimitiveFacade.compareInt( modeClose , StreamIO.MODE_CLOSE_BOTH, StreamIO.MODE_CLOSE_OUT_ONLY );
	}
	
	/**
	 * <p>Pipe the content of a java.io.InputStream into a java.io.OutputStream.</p>
	 * 
	 * <p>StreamIO provides constants for bufferSize and closeMode.</p>
	 * 
	 * @param src				the source
	 * @param dst				the destination
	 * @param bufferSize		buffer size for reading and writing
	 * @param modeClose			the mode for closing source and/or destination
	 * @return					the number of byte read/written
	 * @throws IOException		in case of troubles during the operation
	 * @see org.fugerit.java.core.io.StreamIO
	 */
	public static long pipe( InputStream src, OutputStream dst, int bufferSize, int modeClose ) throws IOException {
		long result = 0;
        byte[] buffer = new byte[bufferSize];
        int read = src.read(buffer);
        while (read>0) {
            dst.write(buffer, 0, read);
            result+=read;
            read = src.read(buffer);
        }
        if ( checkCloseInput( modeClose ) ) {
            src.close();
        }
        if ( checkCloseOutput( modeClose ) ) {
            dst.close();
        }        
        return result;
	}
	  
	/**
	 * <p>Pipe the content of a java.io.Reader into a java.io.Writer.</p>
	 * 
	 * <p>StreamIO provides constants for bufferSize and closeMode.</p>
	 * 
	 * @param src				the source
	 * @param dst				the destination
	 * @param bufferSize		buffer size for reading and writing
	 * @param modeClose			the mode for closing source and/or destination
	 * @return					the number of char read/written
	 * @throws IOException		in case of troubles during the operation
	 * @see org.fugerit.java.core.io.StreamIO
	 */
    public static long pipe( Reader src, Writer dst, int bufferSize, int modeClose ) throws IOException {
        int result = 0;
        char[] buffer = new char[bufferSize];
        int read = src.read(buffer);
        while (read>0) {
            dst.write(buffer, 0, read);
            result+=read;
            read = src.read(buffer);
        }
        if ( checkCloseInput( modeClose ) ) {
            src.close();
        }
        if ( checkCloseOutput( modeClose ) ) {
            dst.close();
        }   
        return result;
    }    

    /**
	 * <p>Pipe the content of a java.io.InputStream into a java.io.OutputStream.</p>
     * 
     * <p>StreamIO.BUFFERSIZE_DEFAULT is used as bufferSize, both source and destination are closed at the end.</p>
     * 
  	 * @param src				the source
	 * @param dst				the destination
	 * @return					the number of byte read/written
	 * @throws IOException		in case of troubles during the operation
	 * @see org.fugerit.java.core.io.StreamIO
     */
    public static long pipe( InputStream src, OutputStream dst ) throws IOException {
    	return pipe( src, dst , StreamIO.BUFFERSIZE_DEFAULT, StreamIO.MODE_CLOSE_BOTH );
    }
    
    /**
	 * <p>Pipe the content of a java.io.Reader into a java.io.Writer.</p>
     * 
     * <p>StreamIO.BUFFERSIZE_DEFAULT is used as bufferSize, both source and destination are closed at the end.</p>
     * 
  	 * @param src				the source
	 * @param dst				the destination
	 * @return					the number of byte read/written
	 * @throws IOException		in case of troubles during the operation
	 * @see org.fugerit.java.core.io.StreamIO
     */
    public static long pipe( Reader src, Writer dst ) throws IOException {
    	return pipe( src, dst , StreamIO.BUFFERSIZE_DEFAULT, StreamIO.MODE_CLOSE_BOTH );
    }
    
}
