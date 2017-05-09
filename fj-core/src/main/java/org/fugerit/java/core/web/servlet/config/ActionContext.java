package org.fugerit.java.core.web.servlet.config;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.web.servlet.request.ParamMap;

public class ActionContext implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -4995800761539244745L;


	public static ActionContext newContext( HttpServletRequest request, HttpServletResponse response, ServletContext context ) {
		return new ActionContext(request, response, context);
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public ServletContext getContext() {
		return context;
	}

	public SessionContext getSessionContext() {
		return sessionContext;
	}
	
	public ParamMap getParamMap() {
		return paramMap;
	}

	private ActionContext(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		this.request = request;
		this.response = response;
		this.context = context;
		this.sessionContext = SessionContext.getHttpSessionContext( request );
		this.paramMap = ParamMap.getParamMap( request );
		this.attributeMap = new HashMap();
	}
	
	private Map attributeMap;
	
	private ParamMap paramMap;

	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private ServletContext context;
	
	private SessionContext sessionContext;

	public Object getAttribute( String name ) {
		return this.attributeMap.get( name );
	}
	
	public Object removeAttribute( String name ) {
		return this.attributeMap.remove( name );
	}
	
	public void setAttribute( String name, Object attribute ) {
		this.attributeMap.put( name, attribute );
	}
	
	public Iterator getAttributeNames() {
		return this.attributeMap.keySet().iterator();
	}
	
}
