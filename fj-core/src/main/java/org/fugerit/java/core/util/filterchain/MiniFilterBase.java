package org.fugerit.java.core.util.filterchain;

import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MiniFilterBase implements MiniFilter {

	public static String genKey() {
		return UUID.randomUUID().toString();
	}
	
	protected static Logger logger = LoggerFactory.getLogger( MiniFilter.class );

	protected MiniFilterBase( String key, String description, int defaultBehaviour ) {
		this.key = key;
		this.description = description;
		this.defaultBehaviour = defaultBehaviour;
	}
	
	protected MiniFilterBase( String key, int defaultBehaviour ) {
		this( key, key, defaultBehaviour );
	}
	
	protected MiniFilterBase( int defaultBehaviour ) {
		this( genKey(), defaultBehaviour );
	}
	
	protected MiniFilterBase() {
		this.defaultBehaviour = MiniFilter.CONTINUE;
	}
	
	private String description;
	
	private String key;
	
	private int defaultBehaviour;
	
	private String param01;
	
	private Properties customConfig;
	
	private String chainId;

	@Override
	public Properties getCustomConfig() {
		return customConfig;
	}

	@Override
	public void setCustomConfig(Properties customConfig) {
		this.customConfig = customConfig;
	}
	
	@Override
	public int getDefaultBehaviour() {
		return this.defaultBehaviour;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public String getParam01() {
		return param01;
	}

	public void setParam01(String param01) {
		this.param01 = param01;
	}

	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"["+this.getKey()+",default-behaviour:"+this.getDefaultBehaviour()+"]";
	}
	
	@Override
	public void config(String key, String description, Integer defaultBehaviour) {
		if ( key == null ) {
			key = genKey();
		}
		if ( description == null ) {
			description = key;
		}
		if ( defaultBehaviour == null ) {
			defaultBehaviour = CONTINUE; 
		}
		this.key = key;
		this.description = description;
		this.defaultBehaviour = defaultBehaviour.intValue();
	}

	@Override
	public abstract int apply(MiniFilterContext context, MiniFilterData data) throws Exception;
	
}
