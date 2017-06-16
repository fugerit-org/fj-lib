package org.fugerit.java.core.web.auth.handler;

import javax.servlet.http.HttpServletRequest;

/*
 * basic implementation of auth handler
 * 
 * Some default resource name string are processed :
 * if resource is equal to constant RESOURCE_DISABLED -&gt; return 1 (forbidden)
 * if resource is equal to constant RESOURCE_HIDDEN -&gt; return 2 (hidden)
 * otherwise return 0 (authorized)
 * 
 * @author Fugerit
 *
 */
public class BasicAuthHandler implements AuthHandler {

	/* 
	 * Name for disabled resource ("disabled")
	 */
	public static final String RESOURCE_DISABLED = "disabled";
	
	/* 
	 * Name for hidden resource ("hidden")
	 */	
	public static final String RESOURCE_HIDDEN = "hidden";
	
	@Override
	public int checkAuth(HttpServletRequest request, String resource) {
		int res = AUTH_AUTHORIZED; 
		if ( RESOURCE_DISABLED.equalsIgnoreCase( resource ) ) {
			res = AUTH_FORBIDDEN;
		} else if ( RESOURCE_HIDDEN.equalsIgnoreCase( resource ) ) {
			res = AUTH_HIDDEN;
		}
		return res;
	}

}
