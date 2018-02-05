package org.fugerit.java.core.web.navmap.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.fugerit.java.core.web.navmap.model.NavEntry;
import org.fugerit.java.core.web.navmap.model.NavEntryI;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavFacade {

	private static final Logger logger= LoggerFactory.getLogger(NavFacade.class);
	
	public static int nav( HttpServletRequest request, HttpServletResponse response, NavMap navMap, String reqId ) throws Exception {
		int res = AuthHandler.AUTH_FORBIDDEN;
		String currentUrl = request.getRequestURI().substring( request.getContextPath().length() );
		NavEntryI entry = navMap.getEntryByUrl( currentUrl );
		logger.info( "NavFilter nav() "+reqId+" url - "+currentUrl+", entry - "+entry );
		if ( entry != null ) {
			request.getSession().setAttribute( NavEntry.SESSION_ATT_NAME , entry );
		}
		int authCode = navMap.getAuthHandler().checkAuth( request , entry.getAuth() );
		if ( authCode == AuthHandler.AUTH_AUTHORIZED ) {
			res = AuthHandler.AUTH_AUTHORIZED;
		} else {
			logger.error( "NavFilter nav() "+reqId+" auth error : "+authCode );
			res = AuthHandler.AUTH_AUTHORIZED;	
		}
		return res;
	}
	
}
