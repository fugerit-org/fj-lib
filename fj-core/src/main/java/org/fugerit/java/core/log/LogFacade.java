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
import org.slf4j.LoggerFactory;


/**
 * <p>Facacde per logging handling.</p>
 *
 * @author Fugerit
 *
 */
public class LogFacade {

    private static Logger logger = LoggerFactory.getLogger( LogFacade.class ); 
    
    public static Logger getLog() {
    	return logger;
    }

    public static Logger newLogger( Object c ) {
    	return LoggerFactory.getLogger( c.getClass() );
    }
    
    public static void handleWarn( Throwable t ) {
    	handleWarn( getLog(), t );
    }
    
    public static void handleWarn( Logger logger, Throwable t ) {
    	logger.warn( t.getMessage(), t );
    }
    
    public static void handleError( Throwable t ) {
    	handleError( getLog(), t );
    }
    
    public static void handleError( Logger logger, Throwable t ) {
    	logger.error( t.getMessage(), t );
    }

}

