package org.fugerit.java.core.web.servlet.request.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.web.log.helpers.LogObjectFilter;

public abstract class HttpFilterHelper extends LogObjectFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6077298655157325844L;
	
	private ServletContext context;
	
	@Override
	public void destroy() {

	}

	abstract public void doFilterHttp( HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if ( request instanceof HttpServletRequest && response instanceof HttpServletResponse ) {
			doFilterHttp( (HttpServletRequest)request , (HttpServletResponse)response, chain );
		} else {
			chain.doFilter( request, response );
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		if ( config != null ) {
			this.context = config.getServletContext();
		}
	}

	public ServletContext getContext() {
		return context;
	}
	

}
