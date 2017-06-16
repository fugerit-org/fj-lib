package org.fugerit.java.core.web.navmap.servlet;

import org.fugerit.java.core.web.navmap.model.NavEntry;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.navmap.model.NavMenu;

/*
 * Navigation Data wrapper
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit
 *
 */
public class NavData {

	/*
	 * Attribute name for the current navigation data
	 */
	public static final String ATT_NAME = "CurrentNavData";
	
	private NavEntry entry;

	private NavMap map;
	
	public NavEntry getEntry() {
		return entry;
	}

	public NavData(NavEntry entry, NavMap map) {
		super();
		this.entry = entry;
		this.map = map;
	}
	
	public NavMenu getNavMenu1() {
		return this.map.getMenuById( this.entry.getMenu1() );
	}
	
	public NavMenu getNavMenu2() {
		return this.map.getMenuById( this.entry.getMenu2() );
	}
	
	public NavMenu getNavMenu3() {
		return this.map.getMenuById( this.entry.getMenu3() );
	}
	
	public String getModule() {
		String url = this.entry.getUrl();
		String module = "";
		int index1 = url.indexOf( "/" );
		if ( index1 == 0 ) {
			url = url.substring( 1 );
			index1 = url.indexOf( "/" );
			if ( index1 > 0 ) {
				module = url.substring( 0 , index1 );
			}
		}
		return module;
	}

}
