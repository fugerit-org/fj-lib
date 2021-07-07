package org.fugerit.java.core.web.servlet.request.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class StatusCodeFilter extends HttpFilterHelper {

	public static final String PARAM_DOLOG_TRACE = "log-trace";

	public static final String PARAM_DOLOG_TRACE_DEFAULT = "false";
	
	private boolean doLogException;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		this.doLogException = BooleanUtils.isTrue( StringUtils.valueWithDefault( config.getInitParameter( PARAM_DOLOG_TRACE ) , PARAM_DOLOG_TRACE_DEFAULT ) );
		this.getLogger().info( "init() "+PARAM_DOLOG_TRACE+"="+this.doLogException+"( if true, stack trace is logged in case of exception)" );
	}

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
				StringBuilder log = new StringBuilder();
				log.append( this.getClass().getSimpleName() );
				log.append( " uri : " );
				log.append( request.getRequestURI() );
				log.append( " response status : " );
				log.append( response.getStatus() );	
				if ( exLog != null ) {
					log.append( " exception : " );
					log.append( exLog.toString() );
					if ( this.doLogException ) {
						this.getLogger().error( log.toString(), exLog );
					} else {
						this.getLogger().error( log.toString() );
					}
				} else {
					this.getLogger().warn( log.toString() );
				}
			}
		}
	}

}
