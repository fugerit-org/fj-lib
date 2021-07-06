package org.fugerit.java.core.web.servlet.request;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.web.servlet.context.RequestContext;
import org.w3c.dom.Element;

public class DefaultRequestFilter extends XMLConfigurableObject implements RequestFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1868840485522043700L;

	@Override
	public void filter(RequestContext rq) {
		logger.info( "DO-NOTHING" );
	}

	@Override
	public void configure(Element tag) throws ConfigException {
		logger.info( "DO-NOTHING" );
	}

	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getKey() {
		return this.getId();
	}

}
