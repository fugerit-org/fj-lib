package org.fugerit.java.core.cfg.xml;

public class FactoryCatalog extends CustomListCatalogConfig<FactoryType, ListMapConfig<FactoryType>> {

	/**
	 * Default configuration element for a data list
	 */
	public static final String ATT_TAG_DATA_LIST = "factory";
	
	/**
	 * Default configuration entry for a data entry
	 */
	public static final String ATT_TAG_DATA = "data";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6578973354062035200L;


	public FactoryCatalog() {
		super(ATT_TAG_DATA_LIST, ATT_TAG_DATA);
		this.getGeneralProps().setProperty( ATT_TYPE , FactoryType.class.getName() );
	}

}
