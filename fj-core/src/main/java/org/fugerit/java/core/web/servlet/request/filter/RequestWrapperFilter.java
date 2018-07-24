package org.fugerit.java.core.web.servlet.request.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.web.servlet.request.HttpRequestCacheInput;
import org.fugerit.java.core.web.servlet.response.HttpServletResponseByteData;

public class RequestWrapperFilter extends HttpFilterHelper {

	public static final String PARAM_DOLOG = "do-log";

	public static final String PARAM_DOLOG_DISABLED = "disabled";
	public static final String PARAM_DOLOG_ENABLED = "enabled";
	public static final String PARAM_DOLOG_REQUEST = "request";
	public static final String PARAM_DOLOG_RESPONSE = "response";
	public static final String PARAM_DOLOG_DEFAULT_ENABLED = "enabled";
	
	private static final String[] LIST_PARAM = {
		PARAM_DOLOG_DISABLED, PARAM_DOLOG_ENABLED, PARAM_DOLOG_REQUEST, PARAM_DOLOG_RESPONSE
	};
	
	public static Set<String> DOLOG_REQUEST = StringUtils.newSet( PARAM_DOLOG_ENABLED, PARAM_DOLOG_REQUEST );
	public static Set<String> DOLOG_RESPONSE = StringUtils.newSet( PARAM_DOLOG_ENABLED, PARAM_DOLOG_RESPONSE );
	
	private String doLog;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		this.doLog = StringUtils.valueWithDefault( config.getInitParameter( PARAM_DOLOG ) , PARAM_DOLOG_DEFAULT_ENABLED ).toLowerCase();
		this.getLogger().info( "init() "+PARAM_DOLOG+"="+this.doLog+"( possibile values are : "+Arrays.asList( LIST_PARAM )+" )" );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6794515908483005751L;

	@Override
	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest requestChain = request;
		HttpServletResponse responseChain = response;
		HttpServletResponseByteData responseWrapper = null;
		if ( DOLOG_REQUEST.contains( this.doLog ) ) {
			HttpRequestCacheInput requestWrapper = new HttpRequestCacheInput( request );
			requestChain = requestWrapper;
			this.getLogger().info( "REQUEST: "+new String( requestWrapper.getBuffer() ) );
		}
		if ( DOLOG_RESPONSE.contains( this.doLog ) ) {
			responseWrapper = new HttpServletResponseByteData( response );
			responseChain = responseWrapper;
		}
		chain.doFilter( requestChain, responseChain );
		if ( responseWrapper != null ) {
			this.getLogger().info( "RESPONSE: "+new String( responseWrapper.getBaos().toByteArray() ) );
			response.getOutputStream().write( responseWrapper.getBaos().toByteArray() );
		}
	}

}
