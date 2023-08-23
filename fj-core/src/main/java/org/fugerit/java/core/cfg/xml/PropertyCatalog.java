package org.fugerit.java.core.cfg.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.util.regex.ParamFinder;
import org.fugerit.java.core.util.regex.ParamProvider;
import org.w3c.dom.Element;

public class PropertyCatalog extends ListMapCatalogConfig<PropertyHolder> {

	public PropertyCatalog() {
		super();
		this.getGeneralProps().setProperty( ATT_TYPE , PropertyHolder.class.getName() );
	}

	public static final String PROP_DEFAULT_CATALOG = "default-catalog";
	
	public static final String PROP_MAP_SYSTEM_ENV = "map-system-properties";
	
	public static final String PROP_PATH_PARAM_PROVIDER = "path-param-provider";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8955747963894569155L;

	public static PropertyCatalog loadConfigSafe( String path ) {
		PropertyCatalog result = new PropertyCatalog();
		try ( InputStream is = StreamHelper.resolveStream( path ) ) {
			load( is , result );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "loadConfigSafe" , e );
		}
		return result;
	}
	
	@Override
	protected PropertyHolder customEntryHandling(String dataListId, PropertyHolder current, Element element) throws ConfigException {
		PropertyHolder holder = super.customEntryHandling(dataListId, current, element);
		try {
			if ( this.getParamProvider() != null ) {
				holder.setPath( PARAM_FINDER.substitute( holder.getPath() , this.getParamProvider().getProperties() ) );
			} else  if ( !this.getMapSysEnv().isEmpty() ) {
				holder.setPath( PARAM_FINDER.substitute( holder.getPath() , this.getMapSysEnv() ) );	
			}
			holder.init( this, dataListId );
			this.getLogger().warn( "PropertyCatalog - load ok for holder {} > {}", dataListId, holder.getId() );
		} catch (IOException e) {
			this.getLogger().warn( "PropertyCatalog - Failed init for holder {} > {}", dataListId, holder.getId(), e );
		}
		return holder;
	}

	@Override
	public void configure(Element tag) throws ConfigException {
		super.configure(tag);
		String keyDefaultCatalog = this.getGeneralProps().getProperty( PROP_DEFAULT_CATALOG );
		if ( keyDefaultCatalog != null ) {
			this.defaultCatalog = this.getListMap( keyDefaultCatalog );
		}
		
	}
	
	private static ParamFinder PARAM_FINDER = ParamFinder.newFinder();
	
	private transient ParamProvider pathParamProvider;
	
	private ParamProvider getParamProvider() throws ConfigException {
		if ( this.pathParamProvider == null ) {
			String pathParamProviderType = this.getGeneralProps().getProperty( PROP_PATH_PARAM_PROVIDER );
			if ( pathParamProviderType != null ) {
				this.getLogger().info( PROP_PATH_PARAM_PROVIDER+" -> "+pathParamProviderType );
				try {
					this.pathParamProvider = (ParamProvider) ClassHelper.newInstance( pathParamProviderType );
				} catch (Exception e) {
					throw new ConfigException( e );
				}
			}
		}
		return this.pathParamProvider; 
	}
	
	private Properties mapSysEnv;
	
	private Properties getMapSysEnv() {
		Properties props = this.mapSysEnv;
		if ( this.mapSysEnv == null ) {
			String mapVars = this.getGeneralProps().getProperty( PROP_MAP_SYSTEM_ENV );
			this.mapSysEnv = new Properties();
			if ( StringUtils.isNotEmpty( mapVars ) ) {
				String[] split = mapVars.split( "," );
				for ( int k=0; k<split.length; k++ ) {
					String key = split[k];
					String value = System.getProperty( key );
					this.getLogger().info( "map system env {} -> {}", key, value );
					if ( value != null ) {
						this.mapSysEnv.setProperty( key , value );
					}
				}
			}
			props = this.mapSysEnv;
		}
		return props;
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
