package org.fugerit.java.core.web.navmap.model;

import java.io.Serializable;
import java.util.List;

import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.web.auth.handler.AllowAuthHandler;
import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.fugerit.java.core.web.auth.handler.AuthMapCatalogConfig;
import org.fugerit.java.core.web.servlet.request.RequestFilter;

/*
 * Main modeling object for NavMap library.
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit
 *
 * @see org.fugerit.java.core.web.navmap.model.NavMap
 *
 */
public class NavMap implements Serializable {

	public static final String CONTEXT_ATT_NAME = "org.fugerit.java.mod.web.navmap.model.NavMap#AttName";
	
	public static final String REQUEST_ATT_NAME = "NavMapAttKey";
	
	/*
	 * 
	 */
	private static final long serialVersionUID = -3883392083434225523L;

	private ListMapStringKey<NavEntryI> entryList;
	private ListMapStringKey<NavMenu> menuList;
	private AuthHandler authHandler;
	private List<RequestFilter> requestFilters;
	private AuthMapCatalogConfig authMap;
	
	public NavMap(ListMapStringKey<RequestFilter> requestFilters,
			ListMapStringKey<NavEntryI> entryList, ListMapStringKey<NavMenu> menuList) {
		super();
		this.entryList = entryList;
		this.menuList = menuList;
		this.requestFilters = requestFilters;
		this.authHandler = new AllowAuthHandler();
		this.authMap = new AuthMapCatalogConfig();
	}
	
	public NavEntryI getEntryByUrl( String url ) {
		return this.entryList.get( url );
	}
	
	public NavMenu getMenuById( String id ) {
		return this.menuList.get( id );
	}

	public AuthHandler getAuthHandler() {
		return authHandler;
	}

	public void setAuthHandler(AuthHandler authHandler) {
		this.authHandler = authHandler;
	}

	public List<RequestFilter> getRequestFilters() {
		return requestFilters;
	}

	public AuthMapCatalogConfig getAuthMap() {
		return authMap;
	}
	
}
