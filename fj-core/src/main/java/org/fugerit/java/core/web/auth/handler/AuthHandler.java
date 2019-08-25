package org.fugerit.java.core.web.auth.handler;

import javax.servlet.http.HttpServletRequest;

/*
 * Interface for handling authorization
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit 
 *
 */
public interface AuthHandler {

	public static final String ATT_NAME = "AttAuthHandler";

	/**
	 * User has access to the resource
	 */
	public static final int AUTH_AUTHORIZED = 0;

	/**
	 * User has no access to resource (es. http code 403)
	 */
	public static final int AUTH_FORBIDDEN = 1;
	
	/**
	 * The resouce should be hidden to the user (es. with a http code 404)
	 */
	public static final int AUTH_HIDDEN = 2;
	
	/**
	 * Recently added, subclass of AUTH_FORBIDDEN (could be mapped to http code 401),
	 * The used has not authenticated at all.
	 */
	public static final int AUTH_NOAUTH = 3;
	
	/*
	 * 
	 * @param request		request to check for authorization status
	 * @param resource		resource to check against
	 * @return				0 if authorized, another value otherwise
	 */
	public int checkAuth( HttpServletRequest request, String resource );
	
}
