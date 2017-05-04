package org.fugerit.java.tool;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Basic class for the tool handler interface.</p>
 * 
 * @author Fugerit
 *
 */
public abstract class ToolHandlerHelper implements ToolHandler {

	protected static final Logger logger = LoggerFactory.getLogger(ToolHandlerHelper.class);
	
	public static final int EXIT_KO_DEFAULT = 1;
	
	/**
	 * <p>Handler worker method.</p>
	 * 
	 * @param params			ths params for the tool
	 * @return					exit code (0 is all ok)
	 * @throws Exception		issues if problems arise
	 */
	abstract public int handleWorker( Properties params ) throws Exception;
	
	@Override
	public int handle( Properties params ) throws Exception {
		int exit = EXIT_OK;
		try {
			exit = handleWorker( params );
		} catch ( Exception e ) {
			exit = EXIT_KO_DEFAULT;
			logger.error( "Exception during handleWorker()", e );
		}
		return exit;
	}

}
