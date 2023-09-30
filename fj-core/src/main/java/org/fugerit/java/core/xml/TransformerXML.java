package org.fugerit.java.core.xml;

import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.BooleanUtils;

/*
 * 
 *
 * @author Morozko
 *
 */
public class TransformerXML {

	private TransformerXML() {}
	
	private static final Properties NO_FEATURES = new Properties();
	
	public static TransformerFactory newSafeTransformerFactory( Properties features ) {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			factory.setFeature( XMLConstants.FEATURE_SECURE_PROCESSING, true );
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
			if ( features != null ) {
				for ( Object k : features.keySet() ) {
					String key = String.valueOf( k );
					boolean value = BooleanUtils.isTrue( features.getProperty( key ) );
					factory.setFeature( key, value );
				}
			}
		} catch (TransformerConfigurationException e) {
			throw ConfigRuntimeException.convertExMethod( "newSafeTransformerFactory", e );
		}
		return factory;
	}
	
	public static TransformerFactory newSafeTransformerFactory() {
		return newSafeTransformerFactory( NO_FEATURES );
	}

    public static Transformer newTransformerWithConfig(TransformerConfig config) throws XMLException {
        Transformer transformer = newTransformer();
        config.setAll(transformer);
        return transformer;
    }
	
    public static Transformer newTransformer(Source source, TransformerConfig config) throws XMLException {
        Transformer transformer = newTransformer( source );
        config.setAll(transformer);
        return transformer;
    }
	
    public static Transformer newTransformer(Source source) throws XMLException {
        Transformer transformer = null;
        try {
        	if ( source == null ) {
        		transformer = newSafeTransformerFactory().newTransformer();	
        	} else {
        		transformer = newSafeTransformerFactory().newTransformer(source);	
        	}
        } catch (TransformerConfigurationException tce) {
            throw new XMLException(tce);
        }
        return transformer;
    }

    public static Transformer newTransformer() throws XMLException {
       return newTransformer( null );
    }	
	
}
