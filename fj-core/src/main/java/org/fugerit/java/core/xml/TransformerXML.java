package org.fugerit.java.core.xml;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/*
 * 
 *
 * @author Morozko
 *
 */
public class TransformerXML {

	private TransformerXML() {}
	
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
