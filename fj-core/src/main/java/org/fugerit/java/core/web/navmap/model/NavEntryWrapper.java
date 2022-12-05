package org.fugerit.java.core.web.navmap.model;

import java.io.Serializable;

import org.fugerit.java.core.util.collection.ListMapStringKey;

public class NavEntryWrapper implements NavEntryI, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -238096928448066944L;

	public NavEntryWrapper(NavEntryI entry) {
		super();
		this.entry = entry;
	}

	private NavEntryI entry;

	public NavEntryI getEntry() {
		return entry;
	}
	
	// NavEntryI implementation

	@Override
	public String getUrl() {
		return entry.getUrl();
	}

	@Override
	public String getMenu1() {
		return entry.getMenu1();
	}

	@Override
	public String getMenu2() {
		return entry.getMenu2();
	}

	@Override
	public String getMenu3() {
		return entry.getMenu3();
	}

	@Override
	public String getAuth() {
		return entry.getAuth();
	}

	@Override
	public String getKey() {
		return entry.getKey();
	}

	@Override
	public String getLabel() {
		return entry.getLabel();
	}

	@Override
	public NavEntryI getAliasFor() {
		return entry.getAliasFor();
	}

	@Override
	public NavEntryI getParent() {
		return entry.getParent();
	}

	@Override
	public ListMapStringKey<NavEntryI> getKids() {
		return entry.getKids();
	}

	@Override
	public ListMapStringKey<NavEntryI> getAlias() {
		return entry.getAlias();
	}

	@Override
	public boolean isLeaf() {
		return entry.isLeaf();
	}

	@Override
	public boolean isRoot() {
		return entry.isRoot();
	}

	@Override
	public boolean isAlias() {
		return entry.isAlias();
	}

	@Override
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

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[wrap:"+entry+"]";
	}
	
}
