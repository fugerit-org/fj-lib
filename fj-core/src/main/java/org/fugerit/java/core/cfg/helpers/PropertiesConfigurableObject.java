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

import org.fugerit.java.core.cfg.ConfigException;
import org.w3c.dom.Element;

/**
 * <p>Base class for implementations of ConfigurableObject supporting only
 * the <code>configure(Properties)</code> method.
 * The <code>configure(Element)</code> method will throw an exception.</p>
 *
 * @author Fugerit
 *
 */
public abstract class PropertiesConfigurableObject  extends AbstractConfigurableObject {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2001953242749817211L;

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.cfg.ConfigurableObject#configure(org.w3c.dom.Element)
	 */
	@Override
	public void configure(Element tag) throws ConfigException {
		throw ( new ConfigException( "Properties configuration not supported" ) );
	}

}
