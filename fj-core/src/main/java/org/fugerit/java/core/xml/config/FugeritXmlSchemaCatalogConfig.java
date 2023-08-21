package org.fugerit.java.core.xml.config;

import org.fugerit.java.core.lang.helpers.ClassHelper;

public class FugeritXmlSchemaCatalogConfig extends XMLSchemaCatalogConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6310766098455405695L;

	public static final String PATH_CATALOG = "validate/schema-catalog.xml";
	
	public static FugeritXmlSchemaCatalogConfig init( FugeritXmlSchemaCatalogConfig config ) {
		try {
			loadConfig( ClassHelper.loadFromDefaultClassLoader( PATH_CATALOG ) , config );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
		return config;
	}
	
	private static final FugeritXmlSchemaCatalogConfig INSTANCE = init( new FugeritXmlSchemaCatalogConfig() );
	
	public static FugeritXmlSchemaCatalogConfig getInstance() {
		return INSTANCE;
	}
	
}
