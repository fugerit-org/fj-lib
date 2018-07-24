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
import org.fugerit.java.core.web.servlet.context.RequestContext;
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
	
	private ServletContext context;
	
	@Override
	public void destroy() {
		this.navMap = null;
		this.context = null;
	}

	public void nav( RequestContext requestContext, FilterChain chain ) throws IOException, ServletException {
		String reqId = String.valueOf(  requestContext.getRequest().getSession( true ).getId()+"/"+System.currentTimeMillis() );
		StringBuffer navLog = new StringBuffer();
		boolean doLog = true;
		Exception exLog = null;
		try {
			// generic request pre processing
			NavFacade.requestFilter( requestContext , this.navMap , reqId );
			// actual page processing
			int authCode = NavFacade.nav( requestContext, this.navMap, reqId);
			if ( authCode == AuthHandler.AUTH_AUTHORIZED ) {
				chain.doFilter( requestContext.getRequest(), requestContext.getResponse() );	
				doLog = false;
			} else {
				navLog.append( "auth handler : " );
				navLog.append( authCode );
				if ( authCode == AuthHandler.AUTH_HIDDEN ) {
					requestContext.getResponse().sendError( HttpServletResponse.SC_NOT_FOUND );
				} else {
					requestContext.getResponse().sendError( HttpServletResponse.SC_FORBIDDEN );	
				}
			}
		} catch (Exception e) {
			requestContext.getResponse().sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		} finally {
			if ( doLog ) {
				StringBuffer log = new StringBuffer();
				log.append( this.getClass().getSimpleName() );
				log.append( " nav() " );
				log.append( navLog.toString() );
				log.append( " response status : " );
				log.append( requestContext.getResponse().getStatus() );
				if ( exLog != null ) {
					log.append( " exception: "+exLog.toString() );
					logger.error( log.toString(), exLog );
				} else {
					logger.warn( log.toString() );
				}
			}
		}
	}
	
	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if ( request instanceof HttpServletRequest && response instanceof HttpServletResponse ) {
			RequestContext requestContext = RequestContext.getRequestContext( this.context , (HttpServletRequest)request, (HttpServletResponse)response );
			nav( requestContext , chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			logger.info( "NavFilter init() - start" );
			this.context = filterConfig.getServletContext();
			String config = filterConfig.getInitParameter( "config" );
			logger.info( "NavFilter init() config "+config );
			ServletContext context = filterConfig.getServletContext();
			String basePath = context.getRealPath( "/" );
			File configFile = new File( basePath, config );
			FileInputStream fis = new FileInputStream( configFile );
			NavMap map = NavConfig.parseConfig( fis, basePath );
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
