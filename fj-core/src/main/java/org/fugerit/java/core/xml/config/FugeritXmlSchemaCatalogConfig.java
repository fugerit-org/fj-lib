package org.fugerit.java.core.xml.config;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.lang.helpers.ClassHelper;

public class FugeritXmlSchemaCatalogConfig extends XMLSchemaCatalogConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6310766098455405695L;

	public static final String PATH_CATALOG = "validate/schema-catalog.xml";
	
	private static final FugeritXmlSchemaCatalogConfig INSTANCE = SafeFunction.get( () -> {
		FugeritXmlSchemaCatalogConfig config = new FugeritXmlSchemaCatalogConfig();
		loadConfig( ClassHelper.loadFromDefaultClassLoader( PATH_CATALOG ) , config );
		return config;
	});
	
	public static FugeritXmlSchemaCatalogConfig getInstance() {
		return INSTANCE;
	}
	
}
