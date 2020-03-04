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
package org.fugerit.java.core.log;

import org.slf4j.Logger;

/**
 * Interface for a logging capable object.
 * 
 * Makes ueses of SLF4J
 * 
 * @author Fugerit
 *
 */
public interface LogObject {

	/**
	 * Returnts the logger for this object
	 * 
	 * @return	the logger
	 */
	public Logger getLogger();

}