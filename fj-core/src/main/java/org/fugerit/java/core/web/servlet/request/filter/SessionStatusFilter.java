package org.fugerit.java.core.web.servlet.request.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.web.servlet.helper.ApplicationMapHelper;

public class SessionStatusFilter extends HttpFilterHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8067530891778465203L;
	
	@Override
	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter( request , response );
		StringBuilder buffer = new StringBuilder();
		buffer.append( "session id : " );
		buffer.append( request.getSession().getId() );
		try {
			int size = ApplicationMapHelper.getTotalSessionMapSize( request.getSession() );
			buffer.append( " total session size : " );
			buffer.append( size );
		} catch (Exception e) {
			buffer.append( "failed size calculation " );
			buffer.append( e.toString() );
		}
		this.getLogger().info( buffer.toString() );
	}

}
