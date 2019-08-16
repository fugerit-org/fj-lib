package org.fugerit.java.core.util.filterchain;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.fugerit.java.core.cfg.xml.CustomListCatalogConfig;
import org.fugerit.java.core.cfg.xml.ListMapConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MiniFilterConfig extends CustomListCatalogConfig<MiniFilterConfigEntry, ListMapConfig<MiniFilterConfigEntry>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 286844409632297876L;

	private Map<String, MiniFilterChain> mapChain;
	
	public MiniFilterConfig() {
		this( ATT_TAG_DATA_LIST, ATT_TAG_DATA );
	}

	public MiniFilterConfig(String attTagDataList, String attTagData) {
		super(attTagDataList, attTagData);
		this.mapChain = new HashMap<String, MiniFilterChain>();
		this.getGeneralProps().setProperty( ATT_TYPE , MiniFilterConfigEntry.class.getName() );
	}

	public static MiniFilterConfig loadConfig( InputStream is, MiniFilterConfig config ) throws Exception {
		Document doc = DOMIO.loadDOMDoc( is );
		Element root = doc.getDocumentElement();
		config.configure( root );
		return config;
	}
	
	public MiniFilterChain getChain( String id ) throws Exception {
		ListMapConfig<MiniFilterConfigEntry> c = this.getListMap( id );
		MiniFilterChain chain = new MiniFilterChain();
		chain.getDefaultConfig().putAll( c.getConfig() );
		Iterator<MiniFilterConfigEntry> it = c.iterator();
		while ( it.hasNext() ) {
			MiniFilterConfigEntry entry = it.next();
			String type = entry.getType();
			MiniFilter filter = (MiniFilter) ClassHelper.newInstance( type );
			if ( StringUtils.isNotEmpty( entry.getParam01() ) && filter instanceof MiniFilterBase ) {
				((MiniFilterBase)filter).setParam01( entry.getParam01() );
			}
			filter.config( entry.getKey() , entry.getDescription(), entry.getDefaultBehaviourInt() );
			chain.getFilterChain().add( filter );
			logger.info( "adding filter to chain : "+filter );
		} 
		return chain;
	}
	
	public MiniFilterChain getChainCache( String id ) throws Exception {
		MiniFilterChain chain = this.mapChain.get( id );
		if ( chain == null ) {
			chain = this.getChain( id );
			this.mapChain.put( id , chain );
		}
		return chain;
	}	
	
	public static MiniFilterConfig initFromClassLoaderWithRuntimeException( String path ) {
		MiniFilterConfig config = new MiniFilterConfig();
		try {
			MiniFilterConfig.loadConfig( ClassHelper.loadFromDefaultClassLoader( path ) , config );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
		return config;
	}
	
}
