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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.xml.TransformerXML;
import org.fugerit.java.core.xml.XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Simple IO operations on xml DOM (Document Object Model).
 *
 * @author Fugerit
 *
 */
public class DOMIO {

	public static DocumentBuilderFactory newSafeDocumentBuilderFactory() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		} catch (ParserConfigurationException e) {
			throw ConfigRuntimeException.convertExMethod( "newSafeDocumentBuilderFactory", e );
		}
		return factory;
	}
	
	public static final int DEFAULT_INDENT = 2;
	
    /**
     * Load a DOM structure from a string
     * 
     * @param source    the source
     * @return          the DOM structure
     * @throws XMLException     in case of issues
     */
    public static Document loadDOMDoc(String source) throws XMLException {
        return loadDOMDoc(new StringReader(source));
    }

    /**
     * Load a DOM structure from a source
     * 
     * @param source	the source
     * @param er		an entity resolver
     * @return			the DOM structure
     * @throws XMLException		in case of issues
     */
    public static Document loadDOMDoc(InputSource source, EntityResolver er ) throws XMLException {
        Document result = null;
        try {
            DocumentBuilderFactory factory = newSafeDocumentBuilderFactory();
            DocumentBuilder parser = factory.newDocumentBuilder();
            parser.setEntityResolver( er );
            result = parser.parse(source);
        } catch (Exception e) {
            throw (new XMLException(e));
        }
        return result;
     }    
    
    public static Document loadDOMDoc(InputSource source, EntityResolver er, boolean nsa) throws XMLException {
        Document result = null;
        try {
            DocumentBuilderFactory factory = newSafeDocumentBuilderFactory();
            factory.setNamespaceAware( nsa );
            DocumentBuilder parser = factory.newDocumentBuilder();
            parser.setEntityResolver( er );
            result = parser.parse(source);
        } catch (Exception e) {
            throw (new XMLException(e));
        }
        return result;
     } 
    
    /**
     * Load a DOM structure from a source
     * 
     * @param source	the source
     * @return			the DOM structure
     * @throws XMLException		in case of issues
     */
    public static Document loadDOMDoc(InputSource source) throws XMLException {
       Document result = null;
       try {
           DocumentBuilderFactory factory = newSafeDocumentBuilderFactory(); 
           DocumentBuilder parser = factory.newDocumentBuilder();
           result = parser.parse(source);
       } catch (Exception e) {
           throw (new XMLException(e));
       }
       return result;
    }
    
    public static Document loadDOMDoc(InputSource source, boolean nsa) throws XMLException {
        Document result = null;
        try {
            DocumentBuilderFactory factory = newSafeDocumentBuilderFactory();
            factory.setNamespaceAware( nsa );
            DocumentBuilder parser = factory.newDocumentBuilder();
            result = parser.parse(source);
        } catch (Exception e) {
            throw (new XMLException(e));
        }
        return result;
     }

    /**
     * Load a DOM structure from a Reader
     * 
     * @param source	the source
     * @return			the DOM structure
     * @throws XMLException		in case of issues
     */
    public static Document loadDOMDoc(Reader source) throws XMLException {
        return loadDOMDoc(new InputSource(source));
    }

    public static Document loadDOMDoc(Reader source, boolean nsa) throws XMLException {
        return loadDOMDoc(new InputSource(source), nsa);
    }
    
    /**
     * Load a DOM structure from a stream
     * 
     * @param source	the source
     * @return			the DOM structure
     * @throws XMLException		in case of issues
     */
    public static Document loadDOMDoc(InputStream source) throws XMLException {
        return loadDOMDoc(new InputSource(source));
    }
    
    public static Document loadDOMDoc(InputStream source, boolean nsa) throws XMLException {
        return loadDOMDoc(new InputSource(source), nsa);
    }

    /**
     * Load a DOM structure from a File
     * 
     * @param source	the source
     * @return			the DOM structure
     * @throws XMLException		in case of issues
     */
    public static Document loadDOMDoc(File source) throws XMLException {
        Document result = null;
        try {
            result = loadDOMDoc(new FileInputStream(source));
        } catch (IOException ioe) {
            throw (new XMLException(ioe));
        }
        return result;
    }    
    
    public static Document loadDOMDoc(File source, boolean nsa) throws XMLException {
        Document result = null;
        try {
            result = loadDOMDoc(new FileInputStream(source), nsa);
        } catch (IOException ioe) {
            throw (new XMLException(ioe));
        }
        return result;
    }    
    
    /**
     * Write a DOM structure to a stream
     * 
     * @param tag		the DOM structure
     * @param stream	the destination stream
     * @throws XMLException		in case of issues
     */
    public static void writeDOM(Node tag, OutputStream stream) throws XMLException {
        Transformer transformer = TransformerXML.newTransformer();
        try {
            transformer.transform(new DOMSource(tag), new StreamResult(stream));
        } catch (TransformerException te) {
            throw (new XMLException(te));
        }
    }

    /**
     * Write a DOM structure to a stream, with indentation
     * 
     * @param tag		the DOM structure
     * @param result	the destination stream
     * @throws XMLException		in case of issues
     */
    public static void writeDOMIndent(Node tag, OutputStream result) throws XMLException {
        writeDOMIndent( tag, new StreamResult( result ));
    }
    
    /**
     * Write a DOM structure to a stream, with indentation
     * 
     * @param tag		the DOM structure
     * @param result	the destination stream
     * @throws XMLException		in case of issues
     */
    public static void writeDOMIndent(Node tag, Writer result) throws XMLException {
        writeDOMIndent( tag, new StreamResult( result ) );
    }
    
    
    /**
     * Write a DOM structure to a stream, with indentation
     * 
     * @param tag		the DOM structure
     * @param result	the destination stream
     * @throws XMLException		in case of issues
     */
    public static void writeDOMIndent(Node tag, Result result) throws XMLException {
        writeDOMIndent( tag, result, DEFAULT_INDENT);
    }
    
    /**
     * Write a DOM structure to a stream, with indentation
     * 
     * @param tag		the DOM structure
     * @param result	the destination result
     * @param indent	indent amount
     * @throws XMLException		in case of issues
     */
    public static void writeDOMIndent(Node tag, Result result, int indent) throws XMLException {
        Transformer transformer = TransformerXML.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf( indent ) );
        try {
            transformer.transform(new DOMSource(tag), result );
        } catch (TransformerException te) {
            throw (new XMLException(te));
        }
    }
    
    /**
     * Write a DOM structure to a stream, with indentation
     * 
     * @param tag		the DOM structure
     * @param result	the destination result
     * @param indent	indent amount
     * @throws XMLException		in case of issues
     */
    public static void writeDOMIndent(Node tag, Writer result, int indent) throws XMLException {
        writeDOMIndent( tag, new StreamResult( result ), indent);
    }
    
    private DOMIO() {
        super();
    }
	
}
