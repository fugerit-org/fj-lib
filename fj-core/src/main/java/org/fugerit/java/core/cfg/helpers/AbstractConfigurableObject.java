/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.cfg.helpers;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigurableObject;
import org.fugerit.java.core.cfg.provider.ConfigProvider;
import org.fugerit.java.core.log.LogObject;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Abstract implementation of ConfigurableObject interface.
 * 	Subclasses must only implement configure(Properties) and configure(Document).</p>
 *
 * @author Fugerit
 *
 */
@Slf4j
public abstract class AbstractConfigurableObject implements ConfigurableObject, LogObject, Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -5893401779153563982L;

	protected static Logger logger = log; 	// for backward compatibility
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.cfg.ConfigurableObject#configure(java.util.Properties)
	 */
	@Override
	public abstract void configure(Properties props) throws ConfigException;

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.cfg.ConfigurableObject#configure(org.w3c.dom.Element)
	 */
	@Override	
	public abstract void configure(Element tag) throws ConfigException;

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.cfg.ConfigurableObject#configureProperties(java.io.InputStream)
	 */
	@Override	
	public void configureProperties(InputStream source) throws ConfigException {
		try {
			this.configure( PropsIO.loadFromStream( source ) );
		} catch (Exception e) {
			throw new ConfigException( e );
		}
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.cfg.ConfigurableObject#configureXML(java.io.InputStream)
	 */
	@Override	
	public void configureXML(InputStream source) throws ConfigException {
		try {
			this.configure( DOMIO.loadDOMDoc( source ).getDocumentElement() );
		} catch (Exception e) {
			throw new ConfigException( e );
		}
	}

	@Setter(AccessLevel.PROTECTED)
	@Getter(AccessLevel.PROTECTED)
	private ConfigProvider configProvider;
	
	public static void setConfigProvider( ConfigProvider provider, AbstractConfigurableObject config ) {
		 config.setConfigProvider(provider) ;
	}

	@Override
	public Logger getLogger() {
		return log;
	}
	
}
