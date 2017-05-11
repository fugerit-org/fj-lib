package org.fugerit.java.core.web.auth.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Model object for User data.
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit
 *
 */
public class AuthUser {

	public static final String ATT_NAME = "AuthUser";
	
	public AuthUser(String username, String displayName) {
		super();
		this.username = username;
		this.displayName = displayName;
		this.authList = new ArrayList<String>();
		this.userInfo = null;
	}

	private String username;
	
	private String displayName;
	
	private List<String> authList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private Map<String, Object> userInfo;

	public Map<String, Object> getUserInfo() {
		if ( this.userInfo == null ) {
			this.userInfo = new HashMap<String, Object>();
		}
		return userInfo;
	}

	public void setInfo( String key, Object value ) {
		this.getUserInfo().put( key , value );
	}
	
	public void removeInfo( String key ) {
		this.getUserInfo().remove( key );
		if ( this.getUserInfo().isEmpty() ) {
			this.userInfo = null;
		}
	}

	public List<String> getAuthList() {
		return authList;
	}
	
}
