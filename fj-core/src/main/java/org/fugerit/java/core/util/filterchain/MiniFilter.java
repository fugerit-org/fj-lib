package org.fugerit.java.core.util.filterchain;

public interface MiniFilter {

	/**
	 * On config value is "SKIP"
	 * 
	 * All following steps will be skipped except the one with behaviour "ALWAYS"
	 */
	public static final int SKIP = 0;

	/**
	 * On config value is "CONTINUE"
	 * 
	 * The step will be processed if previous status is CONTINUE
	 */
	public static final int CONTINUE = 1;
	
	/**
	 * On config value is "ALWAYS"
	 * 
	 * The step will be processed even if previous status is skip
	 */
	public static final int ALWAYS = 2;
	
	public String getKey();
	
	public String getDescription();
	
	public int getDefaultBehaviour();
	
	public void config( String key, String description, Integer defaultBehaviour );
	
	public int apply( MiniFilterContext context, MiniFilterData data ) throws Exception;
	
}
