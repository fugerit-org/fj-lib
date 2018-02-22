package org.fugerit.java.core.web.navmap.model;

public class NavEntryAlias extends NavEntryWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6781053773763218934L;

	public NavEntryAlias(NavEntryI entry, String alias) {
		super(entry);
		this.aliasUrl = alias;
	}

	private String aliasUrl;

	@Override
	public String getKey() {
		return aliasUrl;
	}
	
	public String getOriginalKey() {
		return super.getKey();
	}

}
