package org.fugerit.java.core.cfg.xml;

import java.io.IOException;
import java.util.Iterator;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.w3c.dom.Element;

public class PropertyCatalog extends ListMapCatalogConfig<PropertyHolder> {

	public static final String PROP_DEFAULT_CATALOG = "default-catalog";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8955747963894569155L;

	@Override
	public void configure(Element tag) throws ConfigException {
		super.configure(tag);
		Iterator<String> itCatalog = super.getIdSet().iterator();
		while ( itCatalog.hasNext() ) {
			String idCatalog = itCatalog.next();
			ListMapStringKey<PropertyHolder> listProps = this.getListMap( idCatalog );
			Iterator<PropertyHolder> itProps = listProps.iterator();
			while ( itProps.hasNext() ) {
				PropertyHolder holder = itProps.next(); 
				try {
					holder.init();
					logger.warn( "PropertyCatalog - load ok "+idCatalog+">"+holder.getId() );
				} catch (IOException e) {
					logger.warn( "PropertyCatalog - Failed init for holder : "+idCatalog+">"+holder.getId(), e );
				}
			}
		}
		String keyDefaultCatalog = this.getGeneralProps().getProperty( PROP_DEFAULT_CATALOG );
		if ( keyDefaultCatalog != null ) {
			this.defaultCatalog = this.getListMap( keyDefaultCatalog );
		}
	}
	
	private ListMapStringKey<PropertyHolder> defaultCatalog;

	public PropertyHolder getHolder( String catalog, String holder ) {
		return this.getListMap( catalog ).get( holder );
	}
	
	public String getProperty( String catalog, String holder, String key ) {
		return this.getHolder( catalog, holder ).getProperty( key );
	}
	
	public String getProperty( String catalog, String holder, String key, String def ) {
		return this.getHolder( catalog, holder ).getProperty( key, def );
	}
	
	public PropertyHolder getHolderDef( String holder ) {
		return this.getDefaultCatalog().get( holder );
	}
	
	public String getPropertyDef( String holder, String key ) {
		return this.getHolderDef( holder ).getProperty( key );
	}
	
	public String getPropertyDef( String holder, String key, String def ) {
		return this.getHolderDef( holder ).getProperty( key, def );
	}

	public ListMapStringKey<PropertyHolder> getDefaultCatalog() {
		return defaultCatalog;
	}
	
}
