package org.fugerit.java.core.xml.config;

import java.io.InputStream;

import org.fugerit.java.core.cfg.xml.DataListCatalogConfig;

public class XMLSchemaCatalogConfig extends DataListCatalogConfig {

	public static XMLSchemaCatalogConfig loadConfigSchema( InputStream is ) throws Exception {
		return (XMLSchemaCatalogConfig)loadConfig( is, new XMLSchemaCatalogConfig() );
	}
	
	public XMLSchemaCatalogConfig() {
		super( ATT_TAG_SCHEMA_LIST, ATT_TAG_SCHEMA );
	}
	
	public static final String ATT_TAG_SCHEMA_LIST = "schema-list";
	
	public static final String ATT_TAG_SCHEMA = "schema";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067071761917625426L;


}
