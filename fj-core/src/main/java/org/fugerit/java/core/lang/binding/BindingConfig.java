package org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.cfg.xml.ListMapConfig;

public class BindingConfig extends ListMapConfig<BindingFieldConfig> {

	private String tryInit;
	
	public String getTryInit() {
		return tryInit;
	}

	public void setTryInit(String tryInit) {
		this.tryInit = tryInit;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303413217333488788L;

}
