package org.fugerit.java.core.web.navmap.model;

public class NavMenuItem extends NavEntryWrapper {

	private NavEntryI entry;
	
	private String useLabel;

	public NavMenuItem(NavEntryI entry) {
		this( entry, entry.getLabel() );
	}
	
	public NavMenuItem(NavEntryI entry, String useLabel) {
		super( entry);
		this.useLabel = useLabel;
	}

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
	
}
