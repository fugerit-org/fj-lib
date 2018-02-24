package org.fugerit.java.core.web.navmap.model;

public class NavEntryBC extends NavEntryWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4321618353669751552L;

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
