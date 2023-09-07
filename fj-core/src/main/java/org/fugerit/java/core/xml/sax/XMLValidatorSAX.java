package org.fugerit.java.core.xml.sax;

import java.io.IOException;
import java.util.function.Function;

import javax.xml.parsers.SAXParser;

import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.helpers.AbstractXMLValidator;
import org.fugerit.java.core.xml.sax.dh.DefaultHandlerComp;
import org.fugerit.java.core.xml.sax.eh.ResultErrorHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class XMLValidatorSAX extends AbstractXMLValidator {
	
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    public static XMLValidatorSAX newInstance(EntityResolver er) throws XMLException {
        return newInstance(er, false);
    }    
    
    public static XMLValidatorSAX newInstance(Function<SAXParseResult, DefaultHandler> fun, boolean nsa) throws XMLException {
        SAXParser parser = XMLFactorySAX.makeSAXParser(true, nsa);
        return (new XMLValidatorSAX(parser, fun));
    }   
    
    public static XMLValidatorSAX newInstance(EntityResolver er, boolean nsa) throws XMLException {
        return newInstance( r -> new DefaultHandlerComp( er, new ResultErrorHandler( r ) ) , nsa );
    }    

    public static XMLValidatorSAX newInstance() throws XMLException {
        return newInstance(null);
    }    
    
    public static XMLValidatorSAX newInstance(boolean nsa) throws XMLException {
        return newInstance( DefaultHandlerComp.DEFAULT_ER, nsa );
    }
    
    private XMLValidatorSAX(SAXParser parser, Function<SAXParseResult, DefaultHandler> fun) {
    	this.parser = parser;
    	this.fun = fun;
    }
    
    private Function<SAXParseResult, DefaultHandler> fun;
    
    private SAXParser parser;
    
    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(org.xml.sax.InputSource, org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public boolean validateXML(InputSource source, SAXParseResult result) throws XMLException {
        try {
        	this.parser.parse(source, this.fun.apply(result) );
        } catch (IOException | SAXException e) {
            throw (new XMLException(e));
        }
        return this.isValid(result);
    }

}
