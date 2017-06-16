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
package org.fugerit.java.core.db.connect;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple wrapper for a map of ConnectionFactory.
 * 
 * @author Fugerit
 *
 */
public class CfConfig {

	public static final CfConfig EMPTY_CONFIG = new CfConfig();
	
	/**
	 * <p>Default constructor.</p>
	 * 
	 * <p>Creates a new empty CfConfig.</p>
	 * 
	 */
	public CfConfig() {
		this.cfMap = new HashMap<String, ConnectionFactory>();
	}
	
	private Map<String, ConnectionFactory> cfMap;

	/**
	 * Return the wrapped Map.
	 * 
	 * @return The Wrapped map
	 */
	public Map<String, ConnectionFactory> getCfMap() {
		return cfMap;
	}
	
}
