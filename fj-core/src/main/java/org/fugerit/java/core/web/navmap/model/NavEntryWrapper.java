package org.fugerit.java.core.web.navmap.model;

import org.fugerit.java.core.util.collection.ListMapStringKey;

public class NavEntryWrapper implements NavEntryI {

	public NavEntryWrapper(NavEntryI entry) {
		super();
		this.entry = entry;
	}

	private NavEntryI entry;

	public NavEntryI getEntry() {
		return entry;
	}
	
	// NavEntryI implementation

	public String getUrl() {
		return entry.getUrl();
	}

	public String getMenu1() {
		return entry.getMenu1();
	}

	public String getMenu2() {
		return entry.getMenu2();
	}

	public String getMenu3() {
		return entry.getMenu3();
	}

	public String getAuth() {
		return entry.getAuth();
	}

	public String getKey() {
		return entry.getKey();
	}

	public String getLabel() {
		return entry.getLabel();
	}

	public NavEntryI getAliasFor() {
		return entry.getAliasFor();
	}

	public NavEntryI getParent() {
		return entry.getParent();
	}

	public ListMapStringKey<NavEntryI> getKids() {
		return entry.getKids();
	}

	public ListMapStringKey<NavEntryI> getAlias() {
		return entry.getAlias();
	}

	public boolean isLeaf() {
		return entry.isLeaf();
	}

	public boolean isRoot() {
		return entry.isRoot();
	}

	public boolean isAlias() {
		return entry.isAlias();
	}

	public boolean isCurrentBranch(NavEntryI entry) {
		return entry.isCurrentBranch(entry);
	}

	@Override
	public String getDisplay() {
		return entry.getDisplay();
	}

	@Override
	public String getTitle() {
		return entry.getTitle();
	}

	@Override
	public String getInfo1() {
		return entry.getInfo1();
	}

	@Override
	public String getInfo2() {
		return entry.getInfo2();
	}

	@Override
	public String getInfo3() {
		return entry.getInfo3();
	}
	
	
	
}
