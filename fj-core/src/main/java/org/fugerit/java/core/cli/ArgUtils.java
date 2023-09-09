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
package org.fugerit.java.core.cli;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.fugerit.java.core.function.SafeFunction;

/**
 * <p>Simple arg parsing utility.</p>
 * 
 * @author Fugerit
 *
 */
public class ArgUtils {

	private ArgUtils() {}
	
	/**
	 * Prefix for all arguments
	 */
	public static final String ARG_PREFIX = "--";

	/**
	 * Default vaule for arguments withoug value
	 */
	public static final String ARG_DEFAULT_VALUE = "1";
	
	/**
	 * Param file argument
	 * 
	 * If the param-file argument is set, its red as a property file
	 * and each entry is treated as an argument value.
	 */
	public static final String ARG_PARAM_FILE = "param-file";
	
	/**
	 * <p>Create an argument string, ARG_PREFIX+argName.</p>
	 * 
	 * @param argName	the name of the argument
	 * @return			the argument string
	 */
	public static String getArgString( String argName ) {
		return ARG_PREFIX+argName;
	}
	
	/**
	 * <p>Parse an argument list as a property object.</p>
	 * 
	 * @param args		command line arguments
	 * @param checkParamFile	true if param-file should be checked if the argument is set.
	 * @return			the arguments parsed
	 */
	public static Properties getArgs( String[] args, boolean checkParamFile ) {
		return getArgs( args, checkParamFile, true );
	}
	
	/**
	 * <p>Parse an argument list as a property object.</p>
	 * 
	 * @param args		command line arguments
	 * @param checkParamFile	true if param-file should be checked if the argument is set.
	 * @param priorityToParamFile	true if param-file arguments should override command line arguments
	 * @return			the arguments parsed
	 */
	public static Properties getArgs( String[] args, boolean checkParamFile, boolean priorityToParamFile ) {
		Properties props = new Properties();
		if ( args != null ) {
			handleParams(args, props);
		}
		if ( checkParamFile ) {
			checkParamFile(priorityToParamFile, props);
		}
		return props;
	}
	
	private static void handleParams( String[] args, Properties props) {
		String currentkey = null;
		boolean keySet = true;
		for ( int k=0; k<args.length; k++ ) {
			String current = args[k];
			if ( current.startsWith( ARG_PREFIX ) ) {
				currentkey = current.substring( ARG_PREFIX.length() );
				keySet = false;
			} else {
				if ( currentkey != null ) {
					props.setProperty( currentkey , current );
					keySet = true;	
				}
			}
			if ( !keySet ) {
				props.setProperty( currentkey , ARG_DEFAULT_VALUE );
			}
		}
	}
	
	private static void checkParamFile( boolean priorityToParamFile, Properties props ) {
		String paramFile = props.getProperty( ARG_PARAM_FILE );
		if ( paramFile != null ) {
			File f = new File( paramFile );
			SafeFunction.apply(() -> {
				try (FileInputStream fis = new FileInputStream( f )) {
					if ( priorityToParamFile ) {
						props.load( fis );	
					} else {
						Properties toLoad = new Properties();
						toLoad.load( fis );
						Enumeration<Object> keys = toLoad.keys();
						while ( keys.hasMoreElements() ) {
							String currentKey = keys.nextElement().toString();
							if ( !props.containsKey( currentKey ) ) {
								props.setProperty( currentKey , toLoad.getProperty( currentKey ) );
							}
						}
					}
				}
			});
		}
	}
	
	/**
	 * <p>Parse an argument list as a property object.</p>
	 * 
	 * <p>param-file is checked by default at the end.</p>
	 * 
	 * @param args		command line arguments
	 * @return			the arguments parsed
	 */
	public static Properties getArgs( String[] args ) {
		return getArgs( args, true );
	}
	
}
