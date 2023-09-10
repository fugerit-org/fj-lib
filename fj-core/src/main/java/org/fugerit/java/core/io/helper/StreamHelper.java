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
package org.fugerit.java.core.io.helper;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.fugerit.java.core.cfg.CloseHelper;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.SimpleValue;
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

	private StreamHelper() {}

	private static final String URL_HELPER = "://";
	
	public static final String MODE_CLASSLOADER = "cl";
	
	public static final String MODE_FILE = "file";
	
	public static final String MODE_JNDI = "jndi";
	
	public static final String PATH_CLASSLOADER = MODE_CLASSLOADER+URL_HELPER;
	
	public static final String PATH_FILE = MODE_FILE+URL_HELPER;
	
	public static final String PATH_JNDI = MODE_JNDI+URL_HELPER;
	
	public static InputStream resolveStream( String path ) throws IOException {
		return resolveStream( path, null );
	}
	
	public static InputStream resolveStream( String path, String basePath ) throws IOException {
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
	public static <T> InputStream getResourceStream( String path, Class<T> c ) throws IOException {
		return HelperIOException.get( () -> {
			InputStream is = ClassHelper.getDefaultClassLoader().getResourceAsStream( path );
			if ( is == null && c != null ) {
				is = c.getResourceAsStream( path );
			}
			return is;
		});
	}

	public static InputStream resolveStreamByMode( String mode, String path ) throws IOException {	
		return resolveStreamByMode( mode, path, StreamHelper.class );
	}
	
	public static InputStream resolveStreamByMode( String mode, String path, Class<?> c ) throws IOException {	
		return resolveStream( mode+URL_HELPER+path, null, c );
	}
	
	public static InputStream resolveStream( String path, String basePath, Class<?> c ) throws IOException {	
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
    
    /**
     * <p>Close an OutputStream, flushing it</p>
     * 
     * @param out		the java.io.OutputStream to close
     * @return			<code>true</code> if the stream has been closed without exception, <code>false</code> otherwise
     */
    public static boolean closeSafe( OutputStream out ) {
    	return closeSafe( out, true );
    }
    
    /**
     * <p>Close an OutputStream, eventually flushing it</p>
     * 
     * @param out		the java.io.OutputStream to close
     * @param flush		<code>true</code> if the stream should be flushed before closing
     * @return			<code>true</code> if the stream has been closed without exception, <code>false</code> otherwise
     */
    public static boolean closeSafe( OutputStream out, boolean flush ) {
    	return closeSafeHelper( out, flush );
    }
    
    /**
     * <p>Close an InputStream, eventually flushing it</p>
     * 
     * @param in		the java.io.InputStream to close
     * @return			<code>true</code> if the stream has been closed without exception, <code>false</code> otherwise
     */
    public static boolean closeSafe( InputStream in ) {
    	return CloseHelper.closeSilent( in );
    }    
    
    /**
     * <p>Close an Writer, flushing it</p>
     * 
     * @param out		the java.io.Writer to close
     * @return			<code>true</code> if the writer has been closed without exception, <code>false</code> otherwise
     */
    public static boolean closeSafe( Writer out ) {
    	return closeSafe( out, true );
    }
    
    /**
     * <p>Close an Writer, eventually flushing it</p>
     * 
     * @param out		the java.io.Writer to close
     * @param flush		<code>true</code> if the writer should be flushed before closing
     * @return			<code>true</code> if the writer has been closed without exception, <code>false</code> otherwise
     */
    public static boolean closeSafe( Writer out, boolean flush ) {
    	return closeSafeHelper( out, flush );
    }
    
    /**
     * <p>Close an Reader, eventually flushing it</p>
     * 
     * @param in		the java.io.Reader to close
     * @return			<code>true</code> if the reader has been closed without exception, <code>false</code> otherwise
     */
    public static boolean closeSafe( Reader in ) {
    	return CloseHelper.closeSilent( in );
    }  
    
    private static boolean closeSafeHelper( Closeable out, boolean flush ) {
    	SimpleValue<Boolean> res = new SimpleValue<>( false );
    	SafeFunction.applySilent( () ->
    		SafeFunction.applyIfNotNull( out , () -> { 
        		if ( flush && out instanceof Flushable )
        			((Flushable)out).flush();
        		out.close();
        		res.setValue( true );
        	} )
    	);
    	return res.getValue();
    }
   
}
