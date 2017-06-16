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
	
	public static final int AUTH_AUTHORIZED = 0;
	
	public static final int AUTH_FORBIDDEN = 1;
	
	public static final int AUTH_HIDDEN = 2;
	
	/*
	 * 
	 * @param request		request to check for authorization status
	 * @param resource		resource to check against
	 * @return				0 if authorized, another value otherwise
	 */
	public int checkAuth( HttpServletRequest request, String resource );
	
}
