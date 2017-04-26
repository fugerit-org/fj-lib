/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-core
	
 *
 */
package org.fugerit.java.core.lang.helpers;

/**
 * <p>This class provides API for instantiating new classes.</p>
 * 
 * @author Fugerit
 *
 */
public class ClassHelper {

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

	
}
