package org.fugerit.java.core.xml;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

/*
 * 
 *
 * @author Morozko
 *
 */
public class TransformerXML {

	private TransformerXML() {}
	
	public static TransformerFactory newSafeTransformerFactory() {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "newSafeTransformerFactory", e );
		}
		return factory;
	}
	
    public static Transformer newTransformer(Source source) throws XMLException {
        Transformer transformer = null;
        try {
            transformer = newSafeTransformerFactory().newTransformer(source);
        } catch (TransformerConfigurationException tce) {
            throw new XMLException(tce);
        }
        return transformer;
    }

    public static Transformer newTransformer() throws XMLException {
        Transformer transformer = null;
        try {
            transformer = newSafeTransformerFactory().newTransformer();
        } catch (TransformerConfigurationException tce) {
            throw new XMLException(tce);
        }
        return transformer;
    }	
	
}
