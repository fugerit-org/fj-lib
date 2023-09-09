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

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigurableObject;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

/**
 * <p>Default implementation of <code>ConfigurableObject</code>,
 * <code>configure(Properties)</code> method is a donothing, while
 * <code>configure(Element)</code> calls <code>configure(Properties)</code>
 * with the Element attributes transformed in a Properties object.</p>
 *
 * @author Fugerit
 *
 */
public class DefaultConfigurableObject extends AbstractConfigurableObject implements ConfigurableObject {

	/*
	 * 
	 */
	private static final long serialVersionUID = 1596415517623964943L;

	/*
	 * <p>Converts an XML Tag in a Properties object.</p> 
	 * 
	 * <p>Each attribute on the tag is set as a key/value entry in the resulting properties object.</p>
	 * 
	 * @param tag		The tag for configu
	 * @return			The properties
	 */
	public static Properties defaultConversion( Element tag ) {
		return DOMUtils.attributesToProperties( tag );
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.cfg.ConfigurableObject#configure(java.util.Properties)
	 */
	@Override
	public void configure(Properties props) throws ConfigException {
		/*
		 * Just do nothing
		 */
	}

	/* (non-Javadoc)
	 * @see org.fugerit.java.core.cfg.ConfigurableObject#configure(org.w3c.dom.Element)
	 */
	@Override
	public void configure(Element tag) throws ConfigException {
		configure( defaultConversion( tag ) );
	}

}
