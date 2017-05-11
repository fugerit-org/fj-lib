package org.fugerit.java.core.web.auth.handler;

import javax.servlet.http.HttpServletRequest;

public class AllowAuthHandler implements AuthHandler {

	@Override
	public int checkAuth(HttpServletRequest request, String resource) {
		return AUTH_AUTHORIZED;
	}

}
