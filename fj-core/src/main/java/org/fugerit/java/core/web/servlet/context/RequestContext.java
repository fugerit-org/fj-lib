package org.fugerit.java.core.web.servlet.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestContext {

	public static final String ATT_NAME = RequestContext.class.getName()+"_ATT_NAME";
	
	public static RequestContext getRequestContext( ServletContext context, HttpServletRequest request, HttpServletResponse response ) {
		RequestContext requestContext = (RequestContext) request.getAttribute( ATT_NAME );
		if ( requestContext == null ) {
			requestContext = new RequestContext(context, request, response);
		}
		return requestContext;
	}
	
	public RequestContext( ServletContext context, HttpServletRequest request, HttpServletResponse response ) {
		super();
		this.context = context;
		this.request = request;
		this.response = response;
		this.originalRequest = request;
		this.originalResponse = response;
	}

	private ServletContext context;
	
	private HttpServletRequest originalRequest;
	
	private HttpServletResponse originalResponse;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getOriginalRequest() {
		return originalRequest;
	}

	public HttpServletResponse getOriginalResponse() {
		return originalResponse;
	}

	public ServletContext getContext() {
		return context;
	}

}
