package org.fugerit.java.tool;

import java.util.Properties;

/**
 * <p>Interface for every tool in this project.</p>
 * 
 * @author Fugerit
 *
 */
public interface ToolHandler {

	/**
	 * Exit code for operations ended with no errors.
	 */
	public static final int EXIT_OK = 0;
	
	/**
	 * <p>Does the operation of this handler.</p>
	 * 
	 * @param params		the params
	 * @return				the result code of the operation
	 */
	public int handle( Properties params );
	
}
