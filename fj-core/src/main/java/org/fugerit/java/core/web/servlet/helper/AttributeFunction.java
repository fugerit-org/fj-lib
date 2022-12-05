package org.fugerit.java.core.web.servlet.helper;

import org.fugerit.java.core.util.fun.MapFunction;
import org.fugerit.java.core.util.result.Result;

public interface AttributeFunction extends MapFunction<String, Object> {

	@Override
	public Result apply( String key, Object value );
	
}
