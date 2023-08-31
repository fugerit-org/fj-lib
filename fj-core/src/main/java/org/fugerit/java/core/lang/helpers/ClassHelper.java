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
package org.fugerit.java.core.lang.helpers;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>This class provides API for instantiating new classes.</p>
 * 
 * @author Fugerit
 *
 */
@Slf4j
public class ClassHelper {

	private ClassHelper() {}
	
	/**
	 * <p>Return default class loader to instantiate a new class.</p>
	 * 
	 * <p>The source for this method was taken from struts 1 code RequestUtils : 
	 * http://svn.apache.org/repos/asf/struts/struts1/trunk/org.fugerit.java.core/src/main/java/org/apache/struts/util/RequestUtils.java
	 * </p>
	 * 
	 * <p>NOTE : As of 8.2.0 removed throw Exception declaration (now in case a {@link ConfigRuntimeException} will be thrown.</p>
	 * 
	 * @return				the Default ClassLoader.
	 * 
	 */
	public static ClassLoader getDefaultClassLoader() {
		return ObjectUtils.objectWithDefault( Thread.currentThread().getContextClassLoader() , ClassHelper.class.getClassLoader() );
	}
	
	/**
	 * <p>Create a new instance of the given type.</p>
	 * 
	 * <p>NOTE : As of 8.2.0 removed throw Exception declaration (now in case a {@link ConfigRuntimeException} will be thrown.</p>
	 * 
	 * @param type			fully qualified name fo the class for which the new instance will be created
	 * @return				the new istance
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object newInstance( String type ) throws ClassNotFoundException, NoSuchMethodException, ConfigException {
		Object result = null;
		try {
			ClassLoader classLoader = getDefaultClassLoader();
			Class<?> c = classLoader.loadClass( type );
			result = c.getDeclaredConstructor().newInstance();	
		} catch (Exception e) {
			if ( e instanceof ClassNotFoundException ) {
				throw (ClassNotFoundException)e;
			} else if ( e instanceof NoSuchMethodException ) {
				throw (NoSuchMethodException)e;
			} else {
				throw ConfigException.convertExMethod( "newInstance" , e );
			}
		}
		return result;
	}

	public static InputStream loadFromClassLoader( String path, ClassLoader... cl ) {
		InputStream is = null;
		if ( cl != null ) {
			for ( int k=0; k<cl.length && is == null; k++ ) {
				if ( cl[k] != null ) {
					is = cl[k].getResourceAsStream( path );
					if ( is == null ) {
						log.trace( "Not found on class loader {}, path {}", k, path );
					}
				}
			}
		}
		if ( is == null ) {
			is = loadFromDefaultClassLoader(path);	
		}
		return is;
	}
	
	public static InputStream loadFromClassLoader( Object caller, String path ) {
		return loadFromClassLoader( path, getDefaultClassLoader(), (caller != null) ? caller.getClass().getClassLoader() : null );
	}
	
	public static InputStream loadFromDefaultClassLoader( String path ) {
		return getDefaultClassLoader().getResourceAsStream( path ); 
	}
	
	public static String toFullClassName( Object c ) {
		String res = null;
		if ( c != null ) {
			res = c.getClass().getName();
		}
		return res;
	}
	
	public static String toSimpleClassName( Object c ) {
		String res = null;
		if ( c != null ) {
			res = c.getClass().getSimpleName();
		}
		return res;
	}
	
}
