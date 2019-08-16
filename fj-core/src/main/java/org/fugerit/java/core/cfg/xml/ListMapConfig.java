package org.fugerit.java.core.cfg.xml;

import java.util.Collection;
import java.util.Properties;

import org.fugerit.java.core.util.collection.KeyMapper;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

public class ListMapConfig<T> extends ListMapStringKey<T> {

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

	public Properties getConfig() {
		return config;
	}

	public void initFromElementAttributes( Element tag ) {
		DOMUtils.attributesToProperties( tag , this.getConfig() );
	}
	
}
