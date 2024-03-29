package org.fugerit.java.tool;

import java.util.Iterator;
import java.util.Properties;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.core.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>The launcher for all the tools.</p>
 * 
 * @author Fugerit
 *
 */
public class Launcher {

	private Launcher() {}
	
	protected static final Logger logger = LoggerFactory.getLogger(Launcher.class);
	
	/**
	 * Argument for the tool handler to use
	 */
	public static final String ARG_TOOL = "tool";

	/**
	 * Argument for the the verbose version
	 */
	public static final String ARG_VERBOSE = "verbose";
	
	/**
	 * Argumento for the help
	 */
	public static final String ARG_HELP = "help";
	
	private static final Properties HANDLER_LIST = PropsIO.loadFromClassLoaderSafe( "tool/config/handler-list.properties" );
	
	public static int handle( Properties params ) {
		return SafeFunction.getWithDefault( () -> 
				{ 
					int exit = ToolHandler.EXIT_OK;
					String toolName = params.getProperty( ARG_TOOL );
					String help = params.getProperty( ARG_HELP );
					String verbose = params.getProperty( ARG_VERBOSE );
					if ( verbose != null ) {
						logger.info( "Setting to verbose output..." );
					}
					ToolHandler handler = null;
					if ( toolName != null ) {
						String toolType = HANDLER_LIST.getProperty( toolName );
						handler = (ToolHandler)ClassHelper.newInstance( toolType );
					}
					if ( toolName == null || help != null ) {
						printHelp();
						if ( handler instanceof ToolHandlerHelper ) {
							logger.info( "Handler help : \n{}", ((ToolHandlerHelper)handler).getHelp() );		
						}
					} else {
						exit = handler.handle( params );
					}
					return exit;
				} , 
				e -> Integer.valueOf( Result.RESULT_CODE_KO ) );
	}
	
	private static void printHelp() {
		logger.info( "fj-tool launcher v 0.0.1 [2017-05-04] quickstart : " );
		logger.info( "\t\t--tool tool name [run the named tool]" );
		logger.info( "\t\t--help [print this help]" );
		logger.info( "\t\t--verbose [verbose output]" );
		logger.info( "\t\ttool valid options : " );
		Iterator<Object> toolIt = HANDLER_LIST.keySet().iterator();
		while ( toolIt.hasNext() ) {
			logger.info( "\t\t{}", toolIt.next() );	
		}
	}
	
	public static void main( String[] args ) {
		Properties params = ArgUtils.getArgs( args );
		int exit = handle( params );
		logger.info( "EXIT -> {}", exit );
		if ( exit != ToolHandler.EXIT_OK ) {
			System.exit( exit );	
		}
	}
	
}
