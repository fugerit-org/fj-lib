package org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.lang.helpers.AttributeHolderDefault;

public class BindingContext extends AttributeHolderDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3049180509498668152L;
	
	private BindingCatalogConfig catalog;

	public BindingCatalogConfig getCatalog() {
		return catalog;
	}

	public void setCatalog(BindingCatalogConfig catalog) {
		this.catalog = catalog;
	}
	
}
