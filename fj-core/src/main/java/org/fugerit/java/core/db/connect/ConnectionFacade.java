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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.fugerit.java.core.log.LogFacade;

/**
 * Helper class for handling ConnectionFactory
 *
 * @author Fugerit
 *
 */
public class ConnectionFacade {

	private static Map<String, ConnectionFactory> cfMap = new HashMap<String, ConnectionFactory>();
	
	public static void registerFactory( String name, ConnectionFactory cf ) {
		cfMap.put( name, cf );
	}
	
	public static ConnectionFactory getFactory( String name ) {
		return (ConnectionFactory)cfMap.get( name );
	}

	public static boolean closeLoose( Connection conn, Statement stm, ResultSet rs ) {
		return closeLoose( conn ) && closeLoose( stm ) && closeLoose( rs );
	}	
	
	public static boolean closeLoose( Connection conn, Statement stm ) {
		return closeLoose( conn ) && closeLoose( stm );
	}
	
	public static boolean closeLoose( ResultSet rs ) {
		boolean close = true;
		if ( rs != null ) {
			try {
				rs.close();
			} catch (Exception e) {
				LogFacade.getLog().warn( "ConnectionFacade.closeLoose(java.sql.Connection) : failed to close connetion : "+rs );
				close = false;
			}
		} else {
			close = false;
		}
		return close;
	}		
	
	public static boolean closeLoose( Statement stm ) {
		boolean close = true;
		if ( stm != null ) {
			try {
				stm.close();
			} catch (Exception e) {
				LogFacade.getLog().warn( "ConnectionFacade.closeLoose(java.sql.Connection) : failed to close connetion : "+stm );
				close = false;
			}
		} else {
			close = false;
		}
		return close;
	}	
	
	public static boolean closeLoose( Connection conn ) {
		boolean close = true;
		if ( conn != null ) {
			try {
				conn.close();
			} catch (Exception e) {
				LogFacade.getLog().warn( "ConnectionFacade.closeLoose(java.sql.Connection) : failed to close connetion : "+conn );
				close = false;
			}
		} else {
			close = false;
		}
		return close;
	}
	
}
