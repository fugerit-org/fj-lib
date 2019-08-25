package org.fugerit.java.core.web.auth.handler;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class AllowAuthHandler implements AuthHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2170437912074340544L;

	@Override
	public int checkAuth(HttpServletRequest request, String resource) {
		return AUTH_AUTHORIZED;
	}

}
