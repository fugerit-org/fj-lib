package org.fugerit.java.core.util.filterchain;

import java.util.Properties;

import org.fugerit.java.core.lang.helpers.AttributeHolderDefault;
import org.fugerit.java.core.lang.helpers.ExHandler;
import org.fugerit.java.core.lang.helpers.ExHandlerDefault;

public class MiniFilterContext extends AttributeHolderDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = -379329829073614664L;
	
	private Properties customConfig;

	private ExHandler exHandler;
	
	public MiniFilterContext() {
		this.customConfig = new Properties();
		this.exHandler = ExHandlerDefault.INSTANCCE;
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

	public ExHandler getExHandler() {
		return exHandler;
	}

	public void setExHandler(ExHandler exHandler) {
		this.exHandler = exHandler;
	}

}
