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
package org.fugerit.java.core.xml.dom;

import java.util.Properties;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 *	Utilities for handling DOM structures 
 *
 * @author Fugerit
 *
 */
public class DOMUtils {

	    /**
	     * Build a property object containing attributes of a xml Element.
	     * 
	     * @param tag	the element
	     * @return		the attribute mapping as a propery object
	     */
	   public static Properties attributesToProperties(Element tag) {
	        Properties props = new Properties();
	        attributesToProperties(tag, props);
	        return props;
	    }

	   /**
	    * Build a property object containing attributes of a xml Element.
	    * 
	    * @param tag	the element
	    * @param props	the properties to fill with element attibutes
	    */
	   public static void attributesToProperties(Element tag, Properties props) {
	        NamedNodeMap atts = tag.getAttributes();
	        for (int k=0; k<atts.getLength(); k++) {
	            Attr a = (Attr)atts.item(k);
	            props.setProperty(a.getName(), a.getValue());
	        }
	    }
	   
	    private DOMUtils() {
	        super();
	    }	
	
}
