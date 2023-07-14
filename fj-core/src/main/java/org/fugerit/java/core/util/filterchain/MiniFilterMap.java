package org.fugerit.java.core.util.filterchain;

import java.util.Set;

public interface MiniFilterMap {

	Set<String> getKeys();
	
	void setChain( String id, MiniFilterChain chain );
	
	MiniFilterChain getChain(String id) throws Exception;

	MiniFilterChain getChainCache(String id) throws Exception;

}