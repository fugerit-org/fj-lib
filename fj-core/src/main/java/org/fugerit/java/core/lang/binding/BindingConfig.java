package org.fugerit.java.core.lang.binding;

import java.util.Properties;

import org.fugerit.java.core.cfg.xml.IdConfigType;
import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapConfigurable;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

public class BindingConfig extends ListMapStringKey<BindingFieldConfig> implements IdConfigType, KeyObject<String>, ListMapConfigurable {

	private BindingCatalogConfig catalog;
	
	private Properties config = new Properties();

	private String id;

	public BindingCatalogConfig getCatalog() {
		return catalog;
	}

	public void setCatalog(BindingCatalogConfig catalog) {
		this.catalog = catalog;
	}

	private String tryInit;
	
	public String getTryInit() {
		return tryInit;
	}

	public void setTryInit(String tryInit) {
		this.tryInit = tryInit;
	}
	
	@Override
	public Properties getConfig() {
		return config;
	}
	
	@Override
	public void initFromElementAttributes( Element tag ) {
		DOMUtils.attributesToProperties( tag , this.getConfig() );
	}
	
	@Override
	public String getKey() {
		return this.getId();
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303413217333488788L;

}
