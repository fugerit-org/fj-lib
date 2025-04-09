package org.fugerit.java.core.xml.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.XMLValidator;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class XMLFactorySAX {

    public XMLValidator newXMLValidator() throws XMLException {
        return XMLValidatorSAX.newInstance();
    }
    
    public XMLValidator newXMLValidator(EntityResolver er) throws XMLException {
        return XMLValidatorSAX.newInstance(er, this.isNamespaceAware());
    }
    
    public static SAXParser makeSAXParser(boolean val, boolean nsa) throws XMLException {
        return (newInstance(val, nsa).newSAXParser());
    }

    public static SAXParser makeSAXParserSecure(boolean val, boolean nsa) throws XMLException {
        return (newInstanceSecure(val, nsa).newSAXParser());
    }
    
    public SAXParser newSAXParser() throws XMLException {
    	return SafeFunction.getEx( () -> this.factory.newSAXParser(), XMLException.CONVERT_FUN );
    }
    
    public static XMLFactorySAX newInstance() throws XMLException {
        return newInstance(false, false);
    }

    public static XMLFactorySAX newInstanceSecure() throws XMLException {
        return newInstanceSecure(false);
    }
    
    public static XMLFactorySAX newInstance(boolean validating) throws XMLException {
        return newInstance(validating, false);
    }

    public static XMLFactorySAX newInstanceSecure(boolean validating) throws XMLException {
        return newInstanceSecure(validating, false);
    }

    public static XMLFactorySAX newInstanceSecure(boolean validating, boolean namespaceAware) throws XMLException {
        return newInstance( validating, namespaceAware, Boolean.TRUE );
    }

    public static XMLFactorySAX newInstance(boolean validating, boolean namespaceAware) throws XMLException {
        return newInstance( validating, namespaceAware, Boolean.FALSE );
    }

    public static XMLFactorySAX newInstance(boolean validating, boolean namespaceAware, boolean secure) throws XMLException {
        return XMLException.get( () -> {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(validating);
            factory.setNamespaceAware(namespaceAware);
            if ( secure ) {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
                factory.setXIncludeAware(false);
            }
            return new XMLFactorySAX( factory );
        } );
    }
    
    public void setValidating(boolean val) {
        this.factory.setValidating(val);
    }
    
    public void setNamespaceAware(boolean nsa) {
        this.factory.setNamespaceAware(nsa);
    }
    
    public void setFeature(String name, boolean value) throws XMLException {
        try {
            this.factory.setFeature(name, value);
        } catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException snr) {
            throw (new XMLException(snr));
        }
    }
    
    public boolean isValidating() {
        return this.factory.isValidating();
    }
    
    public boolean isNamespaceAware() {
        return this.factory.isNamespaceAware();
    }
    
    public boolean getFeature(String name) throws XMLException {
        boolean result = false;
        try {
            result = this.factory.getFeature(name);
        } catch (SAXNotRecognizedException | SAXNotSupportedException | ParserConfigurationException snr) {
            throw (new XMLException(snr));
        }        
        return result;
    }
    
    
    private SAXParserFactory factory;
    
    private XMLFactorySAX(SAXParserFactory factory) {
        super();
        this.factory = factory;
    }

}
