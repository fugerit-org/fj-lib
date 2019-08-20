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
package org.fugerit.java.core.log;

import org.slf4j.Logger;

/**
 * Basic implementation of LogObject interface
 * 
 * @author Fugerit
 *
 */
public class BasicLogObject implements LogObject {
	
	private Logger logger = LogFacade.newLogger( this );

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.log.LogObject#getLogger()
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

}
