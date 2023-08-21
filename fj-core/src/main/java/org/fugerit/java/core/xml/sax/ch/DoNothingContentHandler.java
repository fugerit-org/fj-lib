package org.fugerit.java.core.xml.sax.ch;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class DoNothingContentHandler implements ContentHandler {

	/**
	 * As it is a do nothing implementation, it is safe to use a default (thread safe)
	 */
	public static final ContentHandler DEFAULT = new DoNothingContentHandler();
	
	public DoNothingContentHandler() {
        super();
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
    public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    @Override
    public void endPrefixMapping(String arg0) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    @Override
    public void skippedEntity(String arg0) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    @Override
    public void setDocumentLocator(Locator arg0) {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    @Override
    public void processingInstruction(String arg0, String arg1) throws SAXException {
    	// do nothing implementation

    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    @Override
    public void startPrefixMapping(String arg0, String arg1) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String arg0, String arg1, String arg2) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
    	// do nothing implementation
    }

}
