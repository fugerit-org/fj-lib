package org.fugerit.java.core.util.fun;

import org.fugerit.java.core.util.result.Result;

public interface MapFunction<K,T> {

	public Result apply( K key, T value );
	
}
