package org.fugerit.java.core.util.filterchain;

import java.util.Properties;

import org.fugerit.java.core.cfg.xml.BasicIdConfigType;

public class MiniFilterConfigEntry extends BasicIdConfigType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8847347997351482756L;

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
	
	private String description;
	
	private String type;
	
	private String defaultBehaviour;
	
	private String param01;
	
	private Properties props;
	
	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	@Override
	public String getKey() {
		return this.getId();
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
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[id:"+this.getId()+"]";
	}
	

}
