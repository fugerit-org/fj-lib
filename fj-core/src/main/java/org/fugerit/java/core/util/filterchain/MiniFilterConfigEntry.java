package org.fugerit.java.core.util.filterchain;

public class MiniFilterConfigEntry {

	public final static String DEFAULT_BEHAVIOUR_CONTINUE = "CONTINUE";
	public final static String DEFAULT_BEHAVIOUR_SKIP = "SKIP";
	
	public int getDefaultBehaviourInt() {
		int res = MiniFilter.CONTINUE;
		if ( DEFAULT_BEHAVIOUR_SKIP.equalsIgnoreCase( this.getDefaultBehaviour() ) ) {
			res = MiniFilter.SKIP;
		}
		return res;
	}
	
	private String id;
	
	private String description;
	
	private String type;
	
	private String defaultBehaviour;

	public String getKey() {
		return this.getId();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultBehaviour() {
		return defaultBehaviour;
	}

	public void setDefaultBehaviour(String defaultBehaviour) {
		this.defaultBehaviour = defaultBehaviour;
	}

	
}
