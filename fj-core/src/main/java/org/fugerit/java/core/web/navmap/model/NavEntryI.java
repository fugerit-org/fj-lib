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

	String getKey();

	String getLabel();

	NavEntryI getAliasFor();

	NavEntryI getParent();

	ListMapStringKey<NavEntryI> getKids();

	ListMapStringKey<NavEntryI> getAlias();

	boolean isLeaf();

	boolean isRoot();

	boolean isAlias();

	boolean isCurrentBranch(NavEntryI entry);

}