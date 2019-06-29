package org.fugerit.java.core.util.filterchain;

public class MiniFilterConfigEntry {

	/**
	 * Next step will be processed
	 */
	public final static String DEFAULT_BEHAVIOUR_CONTINUE = "CONTINUE";
	
	/**
	 * All following steps will be skipped unless some of them have "ALWAYS" behaviour
	 */
	public final static String DEFAULT_BEHAVIOUR_SKIP = "SKIP";
	
	/**
	 * This stepp will always be processed, regardless of previous steps status.
	 */
	public final static String DEFAULT_BEHAVIOUR_ALWAYS = "ALWAYS";
	
	public int getDefaultBehaviourInt() {
		int res = MiniFilter.CONTINUE;
		if ( DEFAULT_BEHAVIOUR_SKIP.equalsIgnoreCase( this.getDefaultBehaviour() ) ) {
			res = MiniFilter.SKIP;
		} else if ( DEFAULT_BEHAVIOUR_ALWAYS.equalsIgnoreCase( this.getDefaultBehaviour() ) ) {
			res = MiniFilter.ALWAYS;
		}
		return res;
	}
	
	private String id;
	
	private String description;
	
	private String type;
	
	private String defaultBehaviour;
	
	private String param01;

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

	public String getParam01() {
		return param01;
	}

	public void setParam01(String param01) {
		this.param01 = param01;
	}

}
