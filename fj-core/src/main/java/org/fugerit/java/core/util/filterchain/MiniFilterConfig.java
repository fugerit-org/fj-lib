package org.fugerit.java.core.util.filterchain;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.CustomListCatalogConfig;
import org.fugerit.java.core.cfg.xml.ListMapConfig;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MiniFilterConfig extends CustomListCatalogConfig<MiniFilterConfigEntry, ListMapConfig<MiniFilterConfigEntry>> implements MiniFilterMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 286844409632297876L;

	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	private HashMap<String, MiniFilterChain> mapChain;
	
	public static final String ATT_TAG_PROPERTIES = "properties";
	
	public MiniFilterConfig() {
		this( ATT_TAG_DATA_LIST, ATT_TAG_DATA );
	}

	public MiniFilterConfig(String attTagDataList, String attTagData) {
		super(attTagDataList, attTagData);
		this.mapChain = new HashMap<String, MiniFilterChain>();
		this.getGeneralProps().setProperty( ATT_TYPE , MiniFilterConfigEntry.class.getName() );
	}

	/**
	 * <p>Configure a MiniFilterConfig instance.</p>
	 * 
	 * <p>NOTE: starting from version 8.4.X java.lang.Exception removed in favor of org.fugerit.java.core.cfg.ConfigRuntimeException.</p>
	 * 
	 * @see <a href="https://fuzzymemory.fugerit.org/src/docs/sonar_cloud/java-S112.html">Define and throw a dedicated exception instead of using a generic one.</a>
	 * 
	 * @param is		the configuration stream
	 * @param config	the item to configure (will be configured by side effect too)
	 * @return			the configured item
	 * @throws 			ConfigRuntimeException in case of issues during loading
	 */
	public static MiniFilterConfig loadConfig( InputStream is, MiniFilterConfig config ) {
		return SafeFunction.get( () -> {
			Document doc = DOMIO.loadDOMDoc( is );
			Element root = doc.getDocumentElement();
			config.configure( root );
			return config;
		});
		
	}
	
	/**
	 * <p>Configure a MiniFilterConfig instance as a MiniFilterMap.</p>
	 * 
	 * <p>NOTE: starting from version 8.4.X java.lang.Exception removed in favor of org.fugerit.java.core.cfg.ConfigRuntimeException.</p>
	 * 
	 * @see <a href="https://fuzzymemory.fugerit.org/src/docs/sonar_cloud/java-S112.html">Define and throw a dedicated exception instead of using a generic one.</a>
	 * 
	 * @param is		the configuration stream
	 * @param config	the item to configure (will be configured by side effect too)
	 * @return			the configured item
	 * @throws 			ConfigRuntimeException in case of issues during loading
	 */
	public static MiniFilterMap loadConfigMap( InputStream is, MiniFilterConfig config ) {
		return loadConfig(is, config);
	}
	
	@Override
	protected MiniFilterConfigEntry customEntryHandling(MiniFilterConfigEntry current, Element element) throws ConfigException {
		NodeList propertiesList = element.getElementsByTagName( ATT_TAG_PROPERTIES );
		if ( propertiesList.getLength() == 1 ) {
			Element propsTag = (Element)propertiesList.item( 0 );
			current.setProps( DOMUtils.attributesToProperties( propsTag ) );
		} else if ( propertiesList.getLength() > 1 ) {
			throw new ConfigException( "Element has toot much "+ATT_TAG_PROPERTIES+" tags" );
		}
		return super.customEntryHandling(current, element);
	}

	protected void customFilterConfig( MiniFilter filter, MiniFilterConfigEntry entry ) {
		// do nothing implementation : subclasses should override it if needed
	}
	
	@Override
	public MiniFilterChain getChain( String id ) throws Exception {
		MiniFilterChain chain = null;
		ListMapConfig<MiniFilterConfigEntry> c = this.getListMap( id );
		if ( c != null ) {
			chain = new MiniFilterChain();
			chain.setChainId( id );
			chain.getDefaultConfig().putAll( c.getConfig() );
			Iterator<MiniFilterConfigEntry> it = c.iterator();
			while ( it.hasNext() ) {
				MiniFilterConfigEntry entry = it.next();
				String type = entry.getType();
				MiniFilter filter = (MiniFilter) ClassHelper.newInstance( type );
				if ( filter instanceof MiniFilterBase ) {
					MiniFilterBase filterBase = (MiniFilterBase)filter;
					filterBase.setChainId( id );
					if ( StringUtils.isNotEmpty( entry.getParam01() ) ) {
						filterBase.setParam01( entry.getParam01() );
					}
				}
				filter.config( entry.getKey() , entry.getDescription(), entry.getDefaultBehaviourInt() );
				filter.setCustomConfig( entry.getProps() );
				this.customFilterConfig(filter, entry);
				chain.getFilterChain().add( filter );
				this.getLogger().info( "adding filter to chain : {}", filter );
			} 	
		} else {
			chain = this.mapChain.get( id );
			if ( chain == null ) {
				throw new ConfigException( "Chain not found : "+id );
			}
		}
		return chain;
	}
	
	@Override
	public MiniFilterChain getChainCache( String id ) throws Exception {
		MiniFilterChain chain = this.mapChain.get( id );
		if ( chain == null ) {
			chain = this.getChain( id );
			this.mapChain.put( id , chain );
		}
		return chain;
	}	
	
	@Override
	public Set<String> getKeys() {
		Set<String> set = new HashSet<String>( this.getIdSet() );
		set.addAll( this.mapChain.keySet() );
		return set;
	}

	@Override
	public void setChain(String id, MiniFilterChain chain) {
		this.mapChain.put( id, chain );
	}
	
	/** 
	 * <p>Init a new MiniFilterConfig using a path resolved by {@link StreamHelper}.</p>
	 * 
	 * <p>A {@link ConfigRuntimeException} is thrown if the initialization fail).</p>
	 * 
	 * @param path	the path to be resolved
	 * @return		the initialized object
	 */
	public static MiniFilterConfig loadConfigSafe( String path ) {
		MiniFilterConfig config = new MiniFilterConfig();
		try ( InputStream is = StreamHelper.resolveStream( path ) ) {
			MiniFilterConfig.loadConfig( StreamHelper.resolveStream( path ) , config );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "loadConfigSafe",  e );
		}
		return config;
	}
	
	/** 
	 * <p>Init a new MiniFilterConfig using a path resolved from class loader.</p>
	 * 
	 * <p>A {@link ConfigRuntimeException} is thrown if the initialization fail).</p>
	 * 
	 * @param path	the path to be resolved
	 * @return		the initialized object
	 */
	public static MiniFilterConfig initFromClassLoaderWithRuntimeException( String path ) {
		return loadConfigSafe( StreamHelper.PATH_CLASSLOADER+path );
	}
	
}
