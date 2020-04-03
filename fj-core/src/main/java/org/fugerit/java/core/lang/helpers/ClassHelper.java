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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>This class provides API for instantiating new classes.</p>
 * 
 * @author Fugerit
 *
 */
public class ClassHelper {

	private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);
	
	/**
	 * <p>Return default class loader to instantiate a new class.</p>
	 * 
	 * <p>The source for this method was taken from struts 1 code RequestUtils : 
	 * http://svn.apache.org/repos/asf/struts/struts1/trunk/org.fugerit.java.core/src/main/java/org/apache/struts/util/RequestUtils.java
	 * </p>
	 * 
	 * @return				the Default ClassLoader.
	 * @throws Exception	in case of troubles during the operation
	 */
	public static ClassLoader getDefaultClassLoader() throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if ( classLoader == null ) {
			classLoader = ClassHelper.class.getClassLoader();
		}
		return classLoader;
	}
	
	/**
	 * <p>Create a new instance of the given type.</p>
	 * 
	 * @param type			fully qualified name fo the class for which the new instance will be created
	 * @return				the new istance
	 * @throws Exception	in case of troubles during the operation
	 */
	public static Object newInstance( String type ) throws Exception {
		Object result = null;
		ClassLoader classLoader = getDefaultClassLoader();
		Class<?> c = classLoader.loadClass( type );
		result = c.newInstance();
		return result;
	}

	public static InputStream loadFromClassLoader( Object caller, String path ) throws Exception {
		InputStream is = null;
		try {
			is = getDefaultClassLoader().getResourceAsStream( path );
		} catch (Exception e) {
			logger.warn( "Failed to load from default class loader, trying caller loader ("+e+")" );
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
