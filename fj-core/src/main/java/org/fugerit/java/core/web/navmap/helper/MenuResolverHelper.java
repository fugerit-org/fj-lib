package org.fugerit.java.core.web.navmap.helper;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.fugerit.java.core.web.navmap.model.NavEntryI;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.navmap.servlet.NavData;
import org.fugerit.java.core.web.navmap.servlet.NavFacade;

/**
 * Helper class for handling menu forn NavMap library.
 * 
 * Each nav-entry has a menu1, menu2 and menu3 property.
 * 
 * @author fugerit
 *
 */
public class MenuResolverHelper {

	public static NavEntryI getFirstAuthOnMenu1( HttpServletRequest request ) {
		NavMap map = NavFacade.getFromRequest( request );
		NavData data = NavFacade.getNavData( request, map);
		return getFirstAuth( request, map, data.getNavMenu1().getEntries() );
	}
	
	public static NavEntryI getFirstAuthOnMenu2( HttpServletRequest request ) {
		NavMap map = NavFacade.getFromRequest( request );
		NavData data = NavFacade.getNavData( request, map);
		return getFirstAuth( request, map, data.getNavMenu2().getEntries() );
	}
	
	public static NavEntryI getFirstAuthOnMenu3( HttpServletRequest request ) {
		NavMap map = NavFacade.getFromRequest( request );
		NavData data = NavFacade.getNavData( request, map);
		return getFirstAuth( request, map, data.getNavMenu3() .getEntries() );
	}
	
	protected static <T extends NavEntryI> T getFirstAuth( HttpServletRequest request, NavMap map, Collection<T> entries ) {
		T res = null;
		Iterator<T> it = entries.iterator();
		AuthHandler handler = map.getAuthHandler();
		while ( res == null && it.hasNext() ) {
			T current = it.next();
			if ( handler.checkAuth( request , current.getAuth() ) == AuthHandler.AUTH_AUTHORIZED ) {
				res = current;
			}
		}
		return res;
	}
	
}