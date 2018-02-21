package org.fugerit.java.core.web.servlet.request;

import org.fugerit.java.core.cfg.ConfigurableObject;
import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.web.servlet.context.RequestContext;

public interface RequestFilter extends ConfigurableObject, KeyObject<String> {

	public void filter( RequestContext rq );

	public void setId( String id );
	
	public String getId();

}
