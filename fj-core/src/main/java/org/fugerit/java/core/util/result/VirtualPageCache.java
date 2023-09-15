package org.fugerit.java.core.util.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.fugerit.java.core.log.BasicLogObject;

public class VirtualPageCache<T> extends BasicLogObject implements Serializable {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = -6408904239036858385L;

	public VirtualPageCache() {
		this.cache = new HashMap<String, CacheWrapper<T>>();
	}
	
	private transient HashMap<String, CacheWrapper<T>> cache;
	
	// 12 hours
	private static final long DEFAULT_TTL = TimeUnit.MILLISECONDS.convert( 12, TimeUnit.HOURS ); // was 12*60*60*1000L
	
	// <code>true</code> it the wrapper is still valid
	private boolean checkTtl( CacheWrapper<T> wrapper ) {
		return wrapper.getCacheTime()>(System.currentTimeMillis()-DEFAULT_TTL);
	}
	
	private String buildPageKey( String virtualKey, int currentPage ) {
		return virtualKey+";"+currentPage;
	}
	
	/*
	 * Returns a virtual page contained in the real page.
	 * 
	 * @param virtualKey
	 * @param currentPage
	 * @return
	 */
	public PagedResult<T> getCachedPage( VirtualFinder finder ) {
		int currentPage = finder.getRealCurrentPage();
		String virtualKey = finder.getSearchVirtualKey();
		String key = this.buildPageKey(virtualKey, currentPage);
		CacheWrapper<T> wrapper = this.cache.get( key );
		this.getLogger().debug( "WRAPPER : {}", wrapper );
		PagedResult<T> page = null;
		if ( wrapper != null ) {
			if ( this.checkTtl( wrapper ) ) {
				page = wrapper.getPage();
			} else {
				this.cache.remove( key );
			}
		}
		return page;
	}
	
	public void addPageToCache( PagedResult<T> bufferPage ) {
		int currentPage = bufferPage.getRealCurrentPage();
		String virtualKey = bufferPage.getVirtualSearchKey();
		String key = this.buildPageKey(virtualKey, currentPage);
		this.getLogger().debug( "ADD TO CACHE : {}", key );
		this.cache.put( key , new CacheWrapper<T>( bufferPage ) );
	}
	
}

class CacheWrapper<T> {
	
	public CacheWrapper(PagedResult<T> page) {
		super();
		this.page = page;
		this.cacheTime = System.currentTimeMillis();
	}

	private PagedResult<T> page;
	
	private long cacheTime;

	public PagedResult<T> getPage() {
		return page;
	}
	
	public long getCacheTime() {
		return cacheTime;
	}

}
