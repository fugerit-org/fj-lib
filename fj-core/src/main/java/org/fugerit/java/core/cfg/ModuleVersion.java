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
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.cfg;

import java.util.Properties;

/**
 * <p>Helper class for wrapping a module version meta information.</p>
 * 
 * @author Fugerit
 *
 */
public interface ModuleVersion {

	/**
	 * Returns the name of this module
	 * 
	 * @return	 the name of this module
	 */
	public String getName();

	/**
	 * Returns the version of this module
	 * 
	 * @return	 the version of this module
	 */
	public String getVersion();

	/**
	 * Returns the date of this module
	 * 
	 * @return	 the date of this module
	 */
	public String getDate();
	
	/**
	 * Returns the last time this module was loaded
	 * 
	 * @return	 the last time this module was loaded
	 */	
	public String getLoadTime();
	
	/**
	 * Returns the dependancies (as module names and versions) of this module
	 * 
	 * @return	 the dependeancies of this module
	 */
	public Properties getDependancies();
	
}
