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
package org.fugerit.java.core.cfg;

import java.util.Properties;

import org.fugerit.java.core.lang.helpers.ClassHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Helper class for wrapping a module version meta information.</p>
 * 
 * @author Fugerit
 *
 */
@Slf4j
public class VersionUtils {
	
	private VersionUtils() {}

	public static Properties MODULES = new Properties();

	/**
	 * Register a module
	 * 
	 * @param name			the name of the module to register
	 * @param className		the implementing class for the module
	 */
	public synchronized static void registerModule( String name, String className ) {
		MODULES.setProperty( name , className );
	}
	
	/**
	 * Returns the registered module list
	 * 
	 * @return	the module list
	 */
	public synchronized static Properties getModuleList() {
		return MODULES;
	}
	
	/**
	 * Return a versione string for a module
	 * 
	 * @param moduleName	the module to look for
	 * @return				the version string
	 */
	public static String getVersionString( String moduleName ) {
		String versionString = null;
		String type = getModuleList().getProperty( moduleName );
		if ( type != null ) {
			try {
				Object o = ClassHelper.newInstance( type );
				versionString = findVersionString(o);
			} catch (Exception t1) {
				versionString = "[02] Class module isn't loaded : ("+type+") - "+t1;
				log.warn( versionString, t1 );
			}	
		} else {
			versionString = "[01] Module does not exist";
		}
		return versionString;
	}
	
	private static String findVersionString( Object o ) {
		String versionString = null;
		try {
			ModuleVersion vc = (ModuleVersion) o;
			versionString = vc.getName()+" "+vc.getVersion()+" "+vc.getDate();
		} catch ( Throwable t2 ) {
			versionString = "[03] Impossible to find module version";
			log.warn( versionString, t2 );
		}
		return versionString;
	}
	
}
