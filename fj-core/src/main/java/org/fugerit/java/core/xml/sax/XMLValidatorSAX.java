package org.fugerit.java.core.xml.sax;

import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.helpers.AbstractXMLValidator;
import org.fugerit.java.core.xml.sax.dh.DefaultHandlerComp;
import org.fugerit.java.core.xml.sax.eh.ResultErrorHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class XMLValidatorSAX extends AbstractXMLValidator {
	
    final static String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    final static String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    final static String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    public static XMLValidatorSAX newInstance(EntityResolver er) throws XMLException {
        return newInstance(er, false);
    }    
    
    public static XMLValidatorSAX newInstance(EntityResolver er, boolean nsa) throws XMLException {
        SAXParser parser = XMLFactorySAX.makeSAXParser(true, nsa);
        try {


            
        } catch (Exception e) {
            throw (new XMLException(e));
        }
        return (new XMLValidatorSAX(parser, er));
    }    

    public static XMLValidatorSAX newInstance() throws XMLException {
        return newInstance(null);
    }    
    
    public static XMLValidatorSAX newInstance(boolean nsa) throws XMLException {
        return newInstance(null, nsa);
    }
    
    private XMLValidatorSAX(SAXParser parser, EntityResolver resolver) {
        this.parser = parser;
        this.resolver = resolver;
    }
    
    private SAXParser parser;
    
    private EntityResolver resolver;
    
    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(org.xml.sax.InputSource, org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    public boolean validateXML(InputSource source, SAXParseResult result) throws XMLException {
        try {
            if (this.resolver!=null) {
                parser.parse(source, new DefaultHandlerComp(this.resolver, new ResultErrorHandler(result)));
            } else {
                parser.parse(source, new DefaultHandlerComp(new ResultErrorHandler(result)));
            }
        } catch (IOException ioe) {
            throw (new XMLException(ioe));
        } catch (SAXException sax) {
            throw (new XMLException(sax));
        }
        return this.isValid(result);
    }

}
