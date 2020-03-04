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
package org.fugerit.java.core.cfg;

import java.io.InputStream;
import java.util.Properties;

import org.w3c.dom.Element;

/**
 * <p>A class implementing <code>ConfigurableObject</code> interface 
 *  is an object which could be configured through an XML Document or 
 *  a Properties object.
 *  </p>
 *
 * @author Fugerit
 *
 */
public interface ConfigurableObject {

	/**
	 * <p>Configure the object.</p>
	 * 
	 * @param source				The input source to use for configuration.
	 * @throws ConfigException		If troubles arise during object configuration.
	 */
	public void configureProperties( InputStream source ) throws ConfigException;	
	
	/**
	 * <p>Configure the object.</p>
	 * 
	 * @param source				The input source to use for configuration.
	 * @throws ConfigException		If troubles arise during object configuration.
	 */
	public void configureXML( InputStream source ) throws ConfigException;
	
	/**
	 * <p>Configure the object.</p>
	 * 
	 * @param props					The property object to use for configuration.
	 * @throws ConfigException		If troubles arise during object configuration.
	 */
	public void configure( Properties props ) throws ConfigException;
	
	/**
	 * <p>Configure the object.</p>
	 * 
	 * @param tag					The tag object to use for configuration.
	 * @throws ConfigException		If troubles arise during object configuration.
	 */
	public void configure( Element tag ) throws ConfigException;
	
}
