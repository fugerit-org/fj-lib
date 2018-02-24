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
	
	private String menuTitle;
	
	public String getMenuTitle() {
		return menuTitle;
	}

	private List<NavMenuItem> entries;

	public List<NavMenuItem> getEntries() {
		return entries;
	}

	public String getId() {
		return id;
	}

	@Override
	public String getKey() {
		return this.getId();
	}

	public NavMenu(String id, String menuTitle) {
		super();
		this.id = id;
		this.menuTitle = menuTitle;
		this.entries = new ListMapStringKey<NavMenuItem>();
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()+"[url:"+this.getId()+"]";
	}
	
}
