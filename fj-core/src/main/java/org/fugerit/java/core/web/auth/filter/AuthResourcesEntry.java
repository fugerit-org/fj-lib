package org.fugerit.java.core.web.auth.filter;

import java.util.regex.Pattern;

import org.fugerit.java.core.cfg.xml.BasicIdConfigType;

public class AuthResourcesEntry extends BasicIdConfigType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2243998276561713567L;

	private String pattern;
	
	private String auth;

	private Pattern m;
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		this.m = Pattern.compile( pattern );
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	public boolean match( String value ) {
		return this.m.matcher( value ).find();
	}

}
