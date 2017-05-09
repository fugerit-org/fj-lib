/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.xml;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 * Simple xml Transformer facade.
 *
 * @author Fugerit
 *
 */
public class TransformerXML {

	/**
	 * Create a tranformer from an InputSource
	 * 
	 * @param source		the input source
	 * @return				the transformer
	 * @throws XMLException	in case of issues
	 */
    public static Transformer newTransformer(Source source) throws XMLException {
        Transformer transformer = null;
        try {
            transformer = 
                TransformerFactory.newInstance().newTransformer(source);
        } catch (TransformerConfigurationException tce) {
            throw new XMLException(tce);
        }
        return transformer;
    }

    /**
	 * Create a tranformer
	 * 
	 * @return				the transformer
	 * @throws XMLException	in case of issues
     */
    public static Transformer newTransformer() throws XMLException {
        Transformer transformer = null;
        try {
            transformer = 
                TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException tce) {
            throw new XMLException(tce);
        }
        return transformer;
    }	
	
}
