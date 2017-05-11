package org.fugerit.java.core.web.navmap.model;

import java.io.Serializable;
import java.util.List;

import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;

/*
 * Menu wrapper
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit
 *
 * @see org.fugerit.java.core.web.navmap.model.NavMap
 *
 */
public class NavMenu implements Serializable, KeyObject<String> {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 3693695282316322807L;

	private String id;
	
	private List<NavEntry> entries;

	public List<NavEntry> getEntries() {
		return entries;
	}

	public String getId() {
		return id;
	}

	@Override
	public String getKey() {
		return this.getId();
	}

	public NavMenu(String id) {
		super();
		this.id = id;
		this.entries = new ListMapStringKey<NavEntry>();
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()+"[url:"+this.getId()+"]";
	}
	
}
