package org.fugerit.java.core.web.auth.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUserUtil {

	public static AuthUser lookForUser( HttpServletRequest request ) {
		return (AuthUser) request.getSession().getAttribute( AuthUser.ATT_NAME );
	}
	
	public static AuthUser lookForUser( HttpSession session ) {
		return (AuthUser) session.getAttribute( AuthUser.ATT_NAME );
	}
	
	
	public static void putUserInSession( AuthUser user, HttpServletRequest request ) {
		request.getSession().setAttribute( AuthUser.ATT_NAME , user );
	}
	
}
