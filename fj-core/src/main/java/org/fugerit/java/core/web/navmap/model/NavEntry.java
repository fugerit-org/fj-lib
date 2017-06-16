package org.fugerit.java.core.web.navmap.model;

import java.io.Serializable;
import java.util.Iterator;

import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;

/*
 * NavEntry wrapper
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit
 *
 * @see org.fugerit.java.core.web.navmap.model.NavMap
 *
 */
public class NavEntry implements Serializable, KeyObject<String> {

	public static final String SESSION_ATT_NAME = "org.fugerit.java.mod.web.navmap.model.NavEntry#AttName";
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 3238506516488055084L;

	private String url;
	
	private String label;
	
	private String menu1;
	
	private String menu2;
	
	private String menu3;
	
	private String auth;


	public NavEntry(String url, String label, String menu1, String menu2,
			String menu3, String auth) {
		super();
		this.url = url;
		this.label = label;
		this.menu1 = menu1;
		this.menu2 = menu2;
		this.menu3 = menu3;
		this.auth = auth;
		this.kids = new ListMapStringKey<NavEntry>();
		this.alias = new ListMapStringKey<NavEntry>();
	}

	public String getUrl() {
		return url;
	}

	public String getMenu1() {
		return menu1;
	}

	public String getMenu2() {
		return menu2;
	}

	public String getMenu3() {
		return menu3;
	}

	public String getAuth() {
		return auth;
	}

	@Override
	public String getKey() {
		return this.getUrl();
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return this.getClass().getName()+"[url:"+this.getUrl()+"]";
	}
	
	private NavEntry parent;
	
	private NavEntry aliasFor;
	
	public NavEntry getAliasFor() {
		return aliasFor;
	}

	public void setAliasFor(NavEntry aliasFor) {
		this.aliasFor = aliasFor;
	}

	private ListMapStringKey<NavEntry> kids;
	
	private ListMapStringKey<NavEntry> alias;

	public NavEntry getParent() {
		return parent;
	}

	public void setParent(NavEntry parent) {
		this.parent = parent;
	}

	public ListMapStringKey<NavEntry> getKids() {
		return kids;
	}

	public ListMapStringKey<NavEntry> getAlias() {
		return alias;
	}
	
	public boolean isLeaf() {
		return this.getKids() == null || this.getKids().isEmpty();
	}
	
	public boolean isRoot() {
		return this.getParent() == null;
	}
	
	public boolean isAlias() {
		return this.getAliasFor() != null;
	}
	
	public boolean isCurrentBranch( NavEntry entry ) {
		boolean check = entry.getUrl().equals( this.getUrl() );
		if ( !check ) {
			if ( this.isAlias() ) {
				check = entry.getAliasFor().isCurrentBranch( entry );
			}
			Iterator<NavEntry> it = this.getKids().iterator();
			while ( it.hasNext() && !check ) {
				NavEntry current = it.next();
				check = current.isCurrentBranch( entry );
			}
			it = this.getAlias().iterator();
			while ( it.hasNext() && !check ) {
				NavEntry current = it.next();
				check = current.isCurrentBranch( entry );
			}
		}
		return check;
	}
	
}
