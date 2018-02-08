package org.fugerit.java.core.web.navmap.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.fugerit.java.core.web.navmap.model.NavConfig;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * NavMap library initialization.
 * 
 * Put NavMap opject in the Context.
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit
 *
 * @see org.fugerit.java.core.web.navmap.model.NavMap
 *
 */
public class NavFilter implements Filter {

	private static final Logger logger= LoggerFactory.getLogger(NavFilter.class);
	
	private NavMap navMap;
	
	@Override
	public void destroy() {
		this.navMap = null;
	}

	public void nav( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException {
		String reqId = String.valueOf(  request.getSession( true ).getId()+"/"+System.currentTimeMillis() );
		try {
			int authCode = NavFacade.nav( request, response, this.navMap, reqId);
			if ( authCode == AuthHandler.AUTH_AUTHORIZED ) {
				chain.doFilter(request, response);	
			} else {
				logger.error( "NavFilter nav() "+reqId+" auth error : "+authCode );
				if ( authCode == AuthHandler.AUTH_HIDDEN ) {
					response.sendError( HttpServletResponse.SC_NOT_FOUND );
				} else {
					response.sendError( HttpServletResponse.SC_FORBIDDEN );	
				}
			}
		} catch (Exception e) {
			logger.error( "NavFilter nav() "+reqId+" error : "+e, e );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
	}
	
	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if ( request instanceof HttpServletRequest && response instanceof HttpServletResponse ) {
			nav((HttpServletRequest)request, (HttpServletResponse)response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			logger.info( "NavFilter init() - start" );
			String config = filterConfig.getInitParameter( "config" );
			logger.info( "NavFilter init() config "+config );
			ServletContext context = filterConfig.getServletContext();
			File configFile = new File( context.getRealPath( "/" ), config );
			FileInputStream fis = new FileInputStream( configFile );
			NavMap map = NavConfig.parseConfig( fis );
			AuthHandler authHandler = map.getAuthHandler();
			context.setAttribute( AuthHandler.ATT_NAME, authHandler );
			fis.close();
			context.setAttribute( NavMap.CONTEXT_ATT_NAME , map );
			this.navMap = map;
			logger.info( "NavFilter init() - end" );
		} catch (Exception e) {
			logger.error( "NavFilter init() error", e );
		}
	}

}
