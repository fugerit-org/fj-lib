package org.fugerit.java.core.web.navmap.model;

import org.fugerit.java.core.lang.helpers.StringUtils;

public class NavMenuItem extends NavEntryWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 490529365147151930L;

	private NavEntryI entry;
	
	private String useLabel;
	
	private String altLabel;
	
	private String useAuth;
	
	private String itemInfo1;
	
	private String itemInfo2;
	
	private String itemInfo3;
	
	public NavMenuItem(NavEntryI entry, String useLabel, String altLabel, String itemInfo1, String itemInfo2, String itemInfo3, String useAuth ) {
		super( entry);
		this.useLabel = StringUtils.valueWithDefault( useLabel , entry.getLabel() );
		this.useAuth = StringUtils.valueWithDefault( useAuth , entry.getAuth() );
		this.altLabel = altLabel;
		this.itemInfo1 = itemInfo1;
		this.itemInfo2 = itemInfo2;
		this.itemInfo3 = itemInfo3;
	}

	@Override
	public NavEntryI getEntry() {
		return entry;
	}

	public String getUseLabel() {
		return useLabel;
	}

	@Override
	public String getLabel() {
		return this.getUseLabel();
	}
	
	public String getOriginalLabel() {
		return this.getEntry().getLabel();
	}

	public String getAltLabel() {
		return altLabel;
	}

	public String getItemInfo1() {
		return itemInfo1;
	}

	public String getItemInfo2() {
		return itemInfo2;
	}

	public String getItemInfo3() {
		return itemInfo3;
	}

	@Override
	public String getAuth() {
		return this.useAuth;
	}
	
	public String getOriginalAuth() {
		return this.getEntry().getAuth();
	}
	
}
