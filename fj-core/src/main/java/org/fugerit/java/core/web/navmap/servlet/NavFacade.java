package org.fugerit.java.core.web.navmap.servlet;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.fugerit.java.core.web.navmap.model.NavEntry;
import org.fugerit.java.core.web.navmap.model.NavEntryI;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.servlet.context.RequestContext;
import org.fugerit.java.core.web.servlet.request.RequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavFacade {

	private static final Logger logger= LoggerFactory.getLogger(NavFacade.class);
	
	/**
	 * This method decide the outcome of the access to a give resource.
	 * The NavEntry to check is found through the url in the request.
	 * 
	 * @param request		the http request
	 * @param response		the http response
	 * @param navMap		the nav map configuration
	 * @param reqId			request id
	 * @return				a <code>int</code> representing the authorization for the resource.
	 * 						(use the same convention as for AuthHandler).
	 * @throws Exception	in case of issues during
	 * @see AuthHandler
	 */
	public static int nav( RequestContext requestContext, NavMap navMap, String reqId ) throws Exception {
		HttpServletRequest request = requestContext.getRequest();
		String currentUrl = request.getRequestURI().substring( request.getContextPath().length() );
		NavEntryI entry = navMap.getEntryByUrl( currentUrl );
		logger.info( "NavFilter nav() "+reqId+" url - "+currentUrl+", entry - "+entry );
		if ( entry != null ) {
			request.getSession().setAttribute( NavEntry.SESSION_ATT_NAME , entry );
		}
		int res = navMap.getAuthHandler().checkAuth( request , entry.getAuth() );
		if ( res != AuthHandler.AUTH_AUTHORIZED ) {
			logger.error( "NavFilter nav() "+reqId+" auth error : "+res );
		}
		return res;
	}
	
	public static void requestFilter( RequestContext requestContext, NavMap navMap, String reqId ) throws Exception {
		Iterator<RequestFilter> itFilters = navMap.getRequestFilters().iterator();
		while ( itFilters.hasNext() ) {
			RequestFilter filter = itFilters.next();
			filter.filter( requestContext );
		}
	}
	
}