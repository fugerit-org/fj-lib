package org.fugerit.java.core.web.servlet.config;

import java.util.Properties;


import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.PropertiesConfigurableObject;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

public abstract class BasicConfig extends PropertiesConfigurableObject {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 7942370774582080658L;
	
	private ConfigContext configContext;
	
	public ConfigContext getConfigContext() {
		return configContext;
	}

	public void setConfigContext(ConfigContext configContext) {
		this.configContext = configContext;
	}

	@Override
	public void configure(Element tag) throws ConfigException {
		this.configure( DOMUtils.attributesToProperties( tag ) );
	}

	@Override
	public abstract void configure(Properties props) throws ConfigException;

}
