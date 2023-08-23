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

import org.fugerit.java.core.cfg.ConfigRuntimeException;

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
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if ( classLoader == null ) {
			classLoader = ClassHelper.class.getClassLoader();
		}
		return classLoader;
	}
	
	/**
	 * <p>Create a new instance of the given type.</p>
	 * 
	 * <p>NOTE : As of 8.2.0 removed throw Exception declaration (now in case a {@link ConfigRuntimeException} will be thrown.</p>
	 * 
	 * @param type			fully qualified name fo the class for which the new instance will be created
	 * @return				the new istance
	 */
	public static Object newInstance( String type ) {
		Object result = null;
		try {
			ClassLoader classLoader = getDefaultClassLoader();
			Class<?> c = classLoader.loadClass( type );
			result = c.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "newInstance" , e );
		}
		return result;
	}

	public static InputStream loadFromClassLoader( Object caller, String path ) {
		InputStream is = null;
		try {
			is = getDefaultClassLoader().getResourceAsStream( path );
		} catch (Exception e) {
			log.warn( "Failed to load from default class loader, trying caller loader ("+e+")" );
			is = caller.getClass().getResourceAsStream( path );
		}
		return is;
	}
	
	public static InputStream loadFromDefaultClassLoader( String path ) throws Exception {
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
