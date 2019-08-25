package org.fugerit.java.core.web.auth.handler;

import javax.servlet.http.HttpServletRequest;

public class AllowDenyAuthHandler extends AllowAuthHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7135932187947002456L;

	public static final String RES_ALLOW = "Allow";
	
	public static final String RES_DENY = "Deny";
	
	@Override
	public int checkAuth(HttpServletRequest request, String resource) {
		int res = AUTH_HIDDEN;
		if ( RES_ALLOW.equalsIgnoreCase( resource )  ) {
			res = AUTH_AUTHORIZED;
		} else if ( RES_DENY.equalsIgnoreCase( resource ) ) {
			res = AUTH_FORBIDDEN;
		} else {
			res = customCheck(request, resource);
		}
		return res;
	}

	public int customCheck( HttpServletRequest request, String resource ) {
		return AUTH_HIDDEN;
	}
	
}
