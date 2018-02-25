package org.fugerit.java.core.web.servlet.request.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StatusCodeFilter extends HttpFilterHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6794515908483005751L;

	@Override
	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean doLog = true;
		Exception exLog = null;
		try {
			chain.doFilter( request, response );
			doLog = false;
		} catch (Exception e) {
			exLog = e;
		} finally {
			if ( doLog ) {
				StringBuffer log = new StringBuffer();
				log.append( this.getClass().getSimpleName() );
				log.append( " uri : " );
				log.append( request.getRequestURI() );
				log.append( " response status : " );
				log.append( response.getStatus() );	
				if ( exLog != null ) {
					log.append( " exception : " );
					log.append( exLog.toString() );
					this.getLogger().error( log.toString(), exLog );
				} else {
					this.getLogger().warn( log.toString() );
				}
			}
		}
	}

}
