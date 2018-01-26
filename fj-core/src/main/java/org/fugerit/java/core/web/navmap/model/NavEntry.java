package org.fugerit.java.core.web.navmap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class NavEntry implements Serializable, KeyObject<String>, NavEntryI {

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


	public NavEntry copyWithLabel( String label ) {
		return new NavEntry( this.getUrl() , label, this.getMenu1(), this.getMenu2(), this.getMenu3(), this.getAuth() );
	}
	
	
	public NavEntry(String url, String label, String menu1, String menu2,
			String menu3, String auth) {
		super();
		this.url = url;
		this.label = label;
		this.menu1 = menu1;
		this.menu2 = menu2;
		this.menu3 = menu3;
		this.auth = auth;
		this.kids = new ListMapStringKey<NavEntryI>();
		this.alias = new ListMapStringKey<NavEntryI>();
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getUrl()
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getMenu1()
	 */
	@Override
	public String getMenu1() {
		return menu1;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getMenu2()
	 */
	@Override
	public String getMenu2() {
		return menu2;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getMenu3()
	 */
	@Override
	public String getMenu3() {
		return menu3;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getAuth()
	 */
	@Override
	public String getAuth() {
		return auth;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getKey()
	 */
	@Override
	public String getKey() {
		return this.getUrl();
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return this.getClass().getName()+"[url:"+this.getUrl()+"]";
	}
	
	private NavEntryI parent;
	
	private NavEntryI aliasFor;
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getAliasFor()
	 */
	@Override
	public NavEntryI getAliasFor() {
		return aliasFor;
	}

	public void setAliasFor(NavEntryI aliasFor) {
		this.aliasFor = aliasFor;
	}

	private ListMapStringKey<NavEntryI> kids;
	
	private ListMapStringKey<NavEntryI> alias;

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getParent()
	 */
	@Override
	public NavEntryI getParent() {
		return parent;
	}

	public void setParent(NavEntryI parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getKids()
	 */
	@Override
	public ListMapStringKey<NavEntryI> getKids() {
		return kids;
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#getAlias()
	 */
	@Override
	public ListMapStringKey<NavEntryI> getAlias() {
		return alias;
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return this.getKids() == null || this.getKids().isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#isRoot()
	 */
	@Override
	public boolean isRoot() {
		return this.getParent() == null;
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#isAlias()
	 */
	@Override
	public boolean isAlias() {
		return this.getAliasFor() != null;
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.web.navmap.model.NavEntryI#isCurrentBranch(org.fugerit.java.core.web.navmap.model.NavEntryI)
	 */
	@Override
	public boolean isCurrentBranch( NavEntryI entry ) {
		boolean check = entry.getUrl().equals( this.getUrl() );
		if ( !check ) {
			Iterator<NavEntryI> it = this.getKids().iterator();
			while ( it.hasNext() && !check ) {
				NavEntryI current = it.next();
				check = current.isCurrentBranch( entry );
			}
		}
		return check;
	}
	
	/**
	 * Ancestors list :
	 * - element in position 0 is the argument entry
	 * - next element is parent (recursively)
	 * 
	 * @param entry		the entry to look for ancestors
	 * @return			Ancestor list
	 */
	public static List<NavEntryI> getAncestors( NavEntryI entry ) {
		List<NavEntryI> ancestors = new ArrayList<NavEntryI>();
		while ( entry != null ) {
			ancestors.add( entry );
			entry = entry.getParent();
		}
		return ancestors;
	}
	
}
