package org.fugerit.java.core.cfg.xml;

import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;

public class ListMapCatalogConfig<T extends KeyObject<String>> extends CustomListCatalogConfig<T, ListMapStringKey<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -844996956024455810L;
	
	public ListMapCatalogConfig() {
		super();
	}

	public ListMapCatalogConfig(String attTagDataList, String attTagData) {
		super(attTagDataList, attTagData);
	}

}
