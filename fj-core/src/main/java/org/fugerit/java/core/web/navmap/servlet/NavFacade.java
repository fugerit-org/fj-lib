package org.fugerit.java.core.web.navmap.servlet;

import java.util.Iterator;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
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
	 * @param requestContext		request context
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
		request.setAttribute( NavMap.REQUEST_ATT_NAME , navMap );
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
	
	public static NavMap getFromRequest( HttpServletRequest request ) {
		return (NavMap) request.getAttribute( NavMap.REQUEST_ATT_NAME );
	}
	
	public static NavMap getNavMapFromContext( Servlet s ) {
		return (NavMap) s.getServletConfig().getServletContext().getAttribute( NavMap.CONTEXT_ATT_NAME ); 
	}
	
	public static NavMap getNavMapFromContext( ServletContext context ) {
		return (NavMap) context.getAttribute( NavMap.CONTEXT_ATT_NAME ); 
	}
	
	public static NavEntryI lookUpEntry( HttpServletRequest request ) {
		NavEntryI entry = (NavEntryI) request.getSession().getAttribute( NavEntryI.SESSION_ATT_NAME );
		return entry;
	}
	
	public static NavData getNavData( HttpServletRequest request, NavMap map ) {
		NavEntryI entry = (NavEntryI) request.getSession().getAttribute( NavEntryI.SESSION_ATT_NAME );
		return new NavData( entry, map );
	}
	
	public static NavData getNavData( HttpServletRequest request, ServletContext context ) {
		NavMap map = getNavMapFromContext( context );
		return getNavData(request, map);
	}
	
}
