package org.fugerit.java.tool.test;

import java.util.Properties;

import org.fugerit.java.tool.ToolHandlerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Tool to correct character encoding.</p>
 * 
 * @author Fugerit
 *
 */
public class LoggerTest extends ToolHandlerHelper {

	private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

	@Override
	public int handleWorker(Properties params) throws Exception {
		logger.error( "TEST LOG ERROR" );
		logger.warn( "TEST LOG WARN" );
		logger.info( "TEST LOG INFO" );
		logger.debug( "TEST LOG DEBUG" );
		return EXIT_OK;
	}
		
}

