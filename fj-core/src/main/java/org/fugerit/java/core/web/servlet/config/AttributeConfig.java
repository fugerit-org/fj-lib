package org.fugerit.java.core.web.servlet.config;

import java.util.Iterator;

import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.fugerit.java.core.xml.dom.SearchDOM;
import org.w3c.dom.Element;

public class AttributeConfig extends BasicConfig {

	/*
	 * 
	 */
	private static final long serialVersionUID = 8260541569146010622L;

	@Override
	public void configure(Properties props) throws ConfigException {
		throw ( new ConfigException( "Unsupported" ) );
	}

	@Override
	public void configure(Element tag) throws ConfigException {
		this.getLogger().info( "configure start" );
		SearchDOM search = SearchDOM.newInstance( true , true );
		List<Element> listContext = search.findAllTags( tag , "context-attribute" );
		Iterator<Element> itContext = listContext.iterator();
		while ( itContext.hasNext() ) {
			Element currentContext = (Element)itContext.next();
			Properties props = DOMUtils.attributesToProperties( currentContext );
			String name = props.getProperty( "name" );
			String value = props.getProperty( "value" );
			this.getLogger().info( "configure attribute "+name+"="+value );
			this.getConfigContext().getContext().setAttribute( name , value );
		}
		this.getLogger().info( "configure end" );
	}

}
