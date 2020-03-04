/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.log.LogFacade;

/**
 * <p>Utility for handling java.util.Property objects.</p>
 *
 * @author Fugerit
 *
 */
public class PropsIO {

	/**
	 * <p>Copy all entries from a java.util.Properties to another.</p>
	 * 
	 * <p>No check is made for overwritten entries.</p>
	 * 
	 * @param from	source object
	 * @param to	destination object
	 * @see ClassHelper
	 */
	public static void fill( Properties from, Properties to ) {
		Enumeration<Object> e = from.keys();
		while ( e.hasMoreElements() ) {
			String key = (String)e.nextElement();
			String value = from.getProperty(key);
			if ( key != null && value != null ) {
				to.setProperty(key, value);	
			}
		}
	}
	
	/**
	 * <p>Load a java.util.Properties from the DefaultClassLoader.</p>
	 * 
	 * @param path				the path
	 * @return					the java.util.Properties object
	 */
	public static Properties loadFromClassLoaderSafe( String path ) {
		Properties props = null;
		try {
			props = loadFromStream( ClassHelper.getDefaultClassLoader().getResourceAsStream( path ) );
		} catch (Exception e) {
			LogFacade.getLog().warn( "PropsIO.loadFromClassLoaderSafe() [default to new Properties]"+e.getMessage(), e );
			props = new Properties();
		}
		return props;
	}		
	
	/**
	 * <p>Load a java.util.Properties from the DefaultClassLoader.</p>
	 * 
	 * @param path				the path
	 * @return					the java.util.Properties object
	 * @throws IOException		in case of troubles during the operation
	 */
	public static Properties loadFromClassLoader( String path ) throws IOException {
		Properties props = null;
		try {
			props = loadFromStream( ClassHelper.getDefaultClassLoader().getResourceAsStream( path ) );
		} catch (Exception e) {
			throw new IOException( e );
		}
		return props;
	}		
	
	/**
	 * <p>Load a java.util.Properties from an URL.</p>
	 * 
	 * @param u					the url
	 * @return					the java.util.Properties object
	 * @throws IOException		in case of troubles during the operation
	 */
	public static Properties loadFromURL( String u ) throws IOException {
		return loadFromURL( new URL( u ) );
	}	
	
	/**
	 * <p>Load a java.util.Properties from an URL.</p>
	 * 
	 * @param u					the url
	 * @return					the java.util.Properties object
	 * @throws IOException		in case of troubles during the operation
	 */
	public static Properties loadFromURL( URL u ) throws IOException {
		return loadFromStream( u.openStream() );
	}		
	
	/**
	 * <p>Load a java.util.Properties from a File.</p>
	 * 
	 * @param f					the file
	 * @return					the java.util.Properties object
	 * @throws IOException		in case of troubles during the operation
	 */
	public static Properties loadFromFile( String f ) throws IOException {
		return loadFromStream( new FileInputStream( f ) );
	}	
	
	/**
	 * <p>Load a java.util.Properties from a File.</p>
	 * 
	 * @param f					the file
	 * @return					the java.util.Properties object
	 */
	public static Properties loadFromFileSafe( String f ) {
		Properties props = null;
		try {
			props = loadFromFile( f );
		} catch (Exception e) {
			LogFacade.getLog().warn( "PropsIO.loadFromFileSafe() [default to new Properties]"+e.getMessage(), e );
			props = new Properties();
		}		
		return props;
	}		
	
	/**
	 * <p>Load a java.util.Properties from a File.</p>
	 * 
	 * @param f					the file
	 * @return					the java.util.Properties object
	 * @throws IOException		in case of troubles during the operation
	 */
	public static Properties loadFromFile( File f ) throws IOException {
		return loadFromStream( new FileInputStream( f ) );
	}
	
	/**
	 * <p>Load a java.util.Properties from a Stream.</p>
	 * 
	 * @param is 				the stream
	 * @return					the java.util.Properties object
	 * @throws IOException		in case of troubles during the operation
	 */
	public static Properties loadFromStream( InputStream is ) throws IOException {
		Properties props = new Properties();
		props.load( is );
		is.close();
		return props;
	}
	
}
