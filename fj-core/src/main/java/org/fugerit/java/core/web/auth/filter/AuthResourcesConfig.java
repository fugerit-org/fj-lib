package org.fugerit.java.core.web.auth.filter;

import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.xml.CustomListCatalogConfig;
import org.fugerit.java.core.cfg.xml.ListMapConfig;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AuthResourcesConfig extends CustomListCatalogConfig<AuthResourcesEntry, ListMapConfig<AuthResourcesEntry>> {

	/**
	 * Default configuration element for a data list
	 */
	public static final String ATT_TAG_DATA_LIST = "resources";
	
	/**
	 * Default configuration entry for a data entry
	 */
	public static final String ATT_TAG_DATA = "resource";
	
	public static final String ATT_DEFAULT_CHAIN = "default-chain";
	
	public AuthResourcesConfig() {
		super( ATT_TAG_DATA_LIST, ATT_TAG_DATA );
		this.getGeneralProps().setProperty( ATT_TYPE , AuthResourcesEntry.class.getName() );
		this.getGeneralProps().setProperty( ATT_LIST_TYPE , ListMapConfig.class.getName() );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7851938322931444961L;

	public static AuthResourcesConfig loadConfig( InputStream is ) throws ConfigException {
		AuthResourcesConfig config = new AuthResourcesConfig();
		try {
			Document doc = DOMIO.loadDOMDoc( is );
			Element root = doc.getDocumentElement();
			config.configure( root );
		} catch (XMLException e) {
			throw new ConfigException( "Config error : "+e, e );
		}
		return config;
	}
	
	public AuthResourcesEntry match( String chain, String pattern ) throws ConfigException {
		AuthResourcesEntry entry = null;
		ListMapConfig<AuthResourcesEntry> resources = this.getListMap( chain );
		if ( resources == null ) {
			throw new ConfigException( "Resource chain not found : "+chain );
		}else {
			for ( AuthResourcesEntry current : resources ) {
				if ( current.match( pattern ) ) {
					entry = current;
					break;
				}
			}
		}
		return entry;
	}
	
	public AuthResourcesEntry match( String pattern ) throws ConfigException {
		return this.match( this.getGeneralProps().getProperty( ATT_DEFAULT_CHAIN ), pattern );
	}
	
}
