package org.fugerit.java.core.xml.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.XMLValidator;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
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
    
    public SAXParser newSAXParser() throws XMLException {
        SAXParser parser = null;
        try {
            parser = this.factory.newSAXParser();
        } catch (ParserConfigurationException pce) {
            throw (new XMLException(pce));
        } catch (SAXException se) {
            throw (new XMLException(se));
        }
        return parser;
    }
    
    public static XMLFactorySAX newInstance() throws XMLException {
        return newInstance(false, false);
    }    
    
    public static XMLFactorySAX newInstance(boolean validating) throws XMLException {
        return newInstance(validating, false);
    }
    
    public static XMLFactorySAX newInstance(boolean validating, boolean namespaceAware) throws XMLException {
        SAXParserFactory saxFac = SAXParserFactory.newInstance();
        saxFac.setValidating(validating);
        saxFac.setNamespaceAware(namespaceAware);
        return new XMLFactorySAX(saxFac);
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
        } catch (SAXNotRecognizedException snr) {
            throw (new XMLException(snr));
        } catch (SAXNotSupportedException sns) {
            throw (new XMLException(sns));
        } catch (ParserConfigurationException pce) {
            throw (new XMLException(pce));
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
        } catch (SAXNotRecognizedException snr) {
            throw (new XMLException(snr));
        } catch (SAXNotSupportedException sns) {
            throw (new XMLException(sns));
        } catch (ParserConfigurationException pce) {
            throw (new XMLException(pce));
        }        
        return result;
    }
    
    
    public SAXParserFactory factory;
    
    private XMLFactorySAX(SAXParserFactory factory) {
        super();
        this.factory = factory;
    }

}
