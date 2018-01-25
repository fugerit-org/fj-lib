package org.fugerit.java.core.web.navmap.model;

public class NavEntryBC extends NavEntryWrapper {

	public NavEntryBC(NavEntryI entry, boolean isFirst, boolean isLast) {
		super(entry);
		this.first = isFirst;
		this.last = isLast;
	}

	private boolean first;
	
	private boolean last;

	public boolean isFirst() {
		return first;
	}

	public boolean isLast() {
		return last;
	}
	
}
