package org.fugerit.java.core.cfg.xml;

import java.util.Collection;

public class CustomListCatalogConfig<T, L extends Collection<T>> extends GenericListCatalogConfig<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067071336L;

	public CustomListCatalogConfig() {
		super();
	}

	public CustomListCatalogConfig(String attTagDataList, String attTagData) {
		super(attTagDataList, attTagData);
	}

	/**
	 * Returns one list map of this catalog by ID
	 * 
	 * @param id	id of the catalog
	 * @return		the list map
	 */
	@SuppressWarnings("unchecked")
	public L getListMap(String id) {
		return (L)super.getDataList(id);
	}
	
}
