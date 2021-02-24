package org.fugerit.java.core.util.filterchain;

import java.util.Properties;

import org.fugerit.java.core.lang.helpers.AttributeHolderDefault;

public class MiniFilterContext extends AttributeHolderDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = -379329829073614664L;
	
	private Properties customConfig;

	
	public MiniFilterContext() {
		this.customConfig = new Properties();
	}
	
	public Properties getCustomConfig() {
		return customConfig;
	}
	
	private String chainId;


	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

}
