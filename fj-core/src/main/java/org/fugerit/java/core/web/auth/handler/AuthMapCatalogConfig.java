package org.fugerit.java.core.web.auth.handler;

import java.io.InputStream;

import org.fugerit.java.core.cfg.xml.DataListCatalogConfig;

public class AuthMapCatalogConfig extends DataListCatalogConfig {

	public static AuthMapCatalogConfig loadAuthList( InputStream is ) throws Exception {
		return loadAuthList( is , new AuthMapCatalogConfig() );
	}
	
	public static AuthMapCatalogConfig loadAuthList( InputStream is, AuthMapCatalogConfig authMap ) throws Exception {
		return (AuthMapCatalogConfig)loadConfig( is, authMap );
	}
	
	public AuthMapCatalogConfig() {
		super( ATT_TAG_AUTH_LIST, ATT_TAG_AUTH );
	}
	
	public static final String ATT_TAG_AUTH_LIST = "auth-list";
	
	public static final String ATT_TAG_AUTH = "auth";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067071761917625426L;

}
