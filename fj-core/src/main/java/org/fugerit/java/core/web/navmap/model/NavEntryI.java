package org.fugerit.java.core.web.navmap.model;

import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;

public interface NavEntryI extends KeyObject<String> {

	String getUrl();

	String getMenu1();

	String getMenu2();

	String getMenu3();

	String getAuth();

	String getKey();

	String getLabel();

	NavEntryI getAliasFor();

	NavEntryI getParent();

	ListMapStringKey<NavEntry> getKids();

	ListMapStringKey<NavEntry> getAlias();

	boolean isLeaf();

	boolean isRoot();

	boolean isAlias();

	boolean isCurrentBranch(NavEntryI entry);

}