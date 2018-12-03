package org.fugerit.java.core.util.filterchain;

public interface MiniFilter {

	public static final int SKIP = 0;
	
	public static final int CONTINUE = 1;
	
	public String getKey();
	
	public String getDescription();
	
	public int getDefaultBehaviour();
	
	public void config( String key, String description, Integer defaultBehaviour );
	
	public int apply( MiniFilterContext context, MiniFilterData data ) throws Exception;
	
}
