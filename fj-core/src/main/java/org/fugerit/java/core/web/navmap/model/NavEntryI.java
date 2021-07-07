package org.fugerit.java.core.web.navmap.model;

import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;

public interface NavEntryI extends KeyObject<String> {

	public static final String SESSION_ATT_NAME = "org.fugerit.java.mod.web.navmap.model.NavEntry#AttName";
	
	String getUrl();

	String getMenu1();

	String getMenu2();

	String getMenu3();

	String getAuth();

	@Override
	String getKey();

	NavEntryI getAliasFor();

	NavEntryI getParent();

	ListMapStringKey<NavEntryI> getKids();

	ListMapStringKey<NavEntryI> getAlias();

	boolean isLeaf();

	boolean isRoot();

	boolean isAlias();

	boolean isCurrentBranch(NavEntryI entry);

	/*
	 * Each nav-entry could have 3 informationl property : 
	 * label (required)
	 * display (optional, default to label if not present)
	 * title (optional, default to display if not present)
	 * 
	 */
	
	/**
	 * Returns the label attribute of the nav-entry.
	 * 
	 * NOTE : label property should always be set
	 * 
	 * @return	the label attribute of nav-entry
	 */
	String getLabel();

	/**
	 * Returns the display attribute of the nav-entry.
	 * 
	 * NOTE : display is optional. in case this attribute is 
	 * 		not present two behaviours are supported : 
	 * 		(default) label attribute is returned instead, or
	 * 		(alternative) null is returned.
	 * 
	 * @return	the display attribute of nav-entry
	 */
	String getDisplay();
	
	/**
	 * Returns the title attribute of the nav-entry.
	 * 
	 * NOTE : title is optional. in case this attribute is 
	 * 		not present two behaviours are supported : 
	 * 		(default) display attribute is returned instead, or
	 * 		(alternative) null is returned.
	 * 
	 * @return	the title attribute of nav-entry
	 */
	String getTitle();
	
	/*
	 * Extra attribute info
	 * 
	 */
	
	/**
	 * Custom info for extra behaviour
	 * 
	 * @return	the attribute value
	 */
	String getInfo1();
	
	/**
	 * Custom info for extra behaviour
	 * 
	 * @return	the attribute value
	 */
	String getInfo2();
	
	/**
	 * Custom info for extra behaviour
	 * 
	 * @return	the attribute value
	 */
	String getInfo3();
	
}
