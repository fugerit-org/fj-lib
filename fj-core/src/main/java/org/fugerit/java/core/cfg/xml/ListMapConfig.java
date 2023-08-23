package org.fugerit.java.core.cfg.xml;

import java.util.Collection;
import java.util.Properties;

import org.fugerit.java.core.util.collection.KeyMapper;
import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapConfigurable;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

public class ListMapConfig<T> extends ListMapStringKey<T> implements IdConfigType, KeyObject<String>, ListMapConfigurable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1229366094851449578L;

	private Properties config = new Properties();
	
	public ListMapConfig() {
		super();
	}

	public ListMapConfig(Collection<T> c) {
		super(c);
	}

	public ListMapConfig(int addMode) {
		super(addMode);
	}

	public ListMapConfig(KeyMapper<String, T> keyMapper, int addMode) {
		super(keyMapper, addMode);
	}

	public ListMapConfig(KeyMapper<String, T> keyMapper) {
		super(keyMapper);
	}

	@Override
	public Properties getConfig() {
		return config;
	}
	
	@Override
	public void initFromElementAttributes( Element tag ) {
		DOMUtils.attributesToProperties( tag , this.getConfig() );
	}

	private String id;
	
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
	
}
