package org.fugerit.java.core.xml.sax.dh;

import java.io.IOException;

import org.fugerit.java.core.xml.sax.ch.DoNothingContentHandler;
import org.fugerit.java.core.xml.sax.dtd.DoNothingDTDHandler;
import org.fugerit.java.core.xml.sax.eh.DoNothingErrorHandler;
import org.fugerit.java.core.xml.sax.er.DoNothingEntityResolver;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class DefaultHandlerComp extends DefaultHandler {
    
    public final static ErrorHandler DEFAULT_EH = new DoNothingErrorHandler();
    public final static EntityResolver DEFAULT_ER = new DoNothingEntityResolver();
    public final static DTDHandler DEFAULT_DH = new DoNothingDTDHandler();
    public final static ContentHandler DEFAULT_CH = new DoNothingContentHandler();

    public DefaultHandlerComp() {
        this(DEFAULT_CH, DEFAULT_DH, DEFAULT_ER, DEFAULT_EH);
    }
    
    public DefaultHandlerComp(ContentHandler ch) {
        this(ch, DEFAULT_DH, DEFAULT_ER, DEFAULT_EH);
    }        
    
    public DefaultHandlerComp(DTDHandler dh) {
        this(DEFAULT_CH, dh, DEFAULT_ER, DEFAULT_EH);
    }        
    
    public DefaultHandlerComp(EntityResolver er) {
        this(DEFAULT_CH, DEFAULT_DH, er, DEFAULT_EH);
    }        
    
    public DefaultHandlerComp(ErrorHandler eh) {
        this(DEFAULT_CH, DEFAULT_DH, DEFAULT_ER, eh);
    }        
    
    
    public DefaultHandlerComp(EntityResolver er,
                                ErrorHandler eh) {
        this(DEFAULT_CH, DEFAULT_DH, er, eh);
    }      
    
    public DefaultHandlerComp(DTDHandler dh,
                            EntityResolver er,
                            ErrorHandler eh) {
        this(DEFAULT_CH, dh, er, eh);
    }        
    
    public DefaultHandlerComp(ContentHandler ch, 
                            EntityResolver er,
                            ErrorHandler eh) {
        this(ch, DEFAULT_DH, er, eh);
    }        
    
    public DefaultHandlerComp(ContentHandler ch, 
            DTDHandler dh,
            ErrorHandler eh) {
        this(ch, dh, DEFAULT_ER, eh);
    }    
    
    public DefaultHandlerComp(ContentHandler ch, 
            DTDHandler dh,
            EntityResolver er) {
        this(ch, dh, er, DEFAULT_EH);
    }
    
    public DefaultHandlerComp(ContentHandler ch, 
                                DTDHandler dh,
                                EntityResolver er,
                                ErrorHandler eh) {
        this.wrappedContentHandler = ch;
        this.wrappedDTDHandler = dh;
        this.wrappedEntityResolver = er;
        this.wrappedErrorHandler = eh;
    }
    
    private ContentHandler wrappedContentHandler;

    /*
     * <p>Restituisce il valore di wrappedContentHandler.</p>
     * 
     * @return il valore di wrappedContentHandler.
     */
    public ContentHandler getWrappedContentHandler() {
        return wrappedContentHandler;
    }
    
    /*
     * <p>Imposta wrappedContentHandler.</p>
     * 
     * @param wrappedContentHandler il wrappedContentHandler da impostare.
     */
    public void setWrappedContentHandler(ContentHandler wrappedContentHandler) {
        this.wrappedContentHandler = wrappedContentHandler;
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        this.getWrappedContentHandler().characters(arg0, arg1, arg2);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
        this.getWrappedContentHandler().endDocument();
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {
        this.getWrappedContentHandler().endElement(arg0, arg1, arg2);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    @Override
    public void endPrefixMapping(String arg0) throws SAXException {
        this.getWrappedContentHandler().endPrefixMapping(arg0);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
    public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
            throws SAXException {
        this.getWrappedContentHandler().ignorableWhitespace(arg0, arg1, arg2);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    @Override
    public void processingInstruction(String arg0, String arg1)
            throws SAXException {
        this.getWrappedContentHandler().processingInstruction(arg0, arg1);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    @Override
    public void setDocumentLocator(Locator arg0) {
        this.getWrappedContentHandler().setDocumentLocator(arg0);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    @Override
    public void skippedEntity(String arg0) throws SAXException {
        this.getWrappedContentHandler().skippedEntity(arg0);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        this.getWrappedContentHandler().startDocument();
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String arg0, String arg1, String arg2,
            Attributes arg3) throws SAXException {
        this.getWrappedContentHandler().startElement(arg0, arg1, arg2, arg3);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    @Override
    public void startPrefixMapping(String arg0, String arg1)
            throws SAXException {
        this.getWrappedContentHandler().startPrefixMapping(arg0, arg1);
    }
    
    
    /* (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity(String arg0, String arg1) throws SAXException {
        InputSource source = null;
        try {
            source = this.getWrappedEntityResolver().resolveEntity(arg0, arg1);
        } catch (IOException ioe) {
            throw (new SAXException(ioe));
        }
        return source;
    }
    
    private EntityResolver wrappedEntityResolver;

    /*
     * <p>Restituisce il valore di wrappedEntityResolver.</p>
     * 
     * @return il valore di wrappedEntityResolver.
     */
    public EntityResolver getWrappedEntityResolver() {
        return wrappedEntityResolver;
    }
    
    /*
     * <p>Imposta wrappedEntityResolver.</p>
     * 
     * @param wrappedEntityResolver il wrappedEntityResolver da impostare.
     */
    public void setWrappedEntityResolver(EntityResolver wrappedEntityResolver) {
        this.wrappedEntityResolver = wrappedEntityResolver;
    }    
    
    /* (non-Javadoc)
     * @see org.xml.sax.DTDHandler#notationDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void notationDecl(String arg0, String arg1, String arg2)
            throws SAXException {
        this.getWrappedDTDHandler().notationDecl(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see org.xml.sax.DTDHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void unparsedEntityDecl(String arg0, String arg1, String arg2,
            String arg3) throws SAXException {
        this.getWrappedDTDHandler().unparsedEntityDecl(arg0, arg1, arg2, arg3);
    }
    
    private DTDHandler wrappedDTDHandler;

    /*
     * <p>Restituisce il valore di wrappedDTDHandler.</p>
     * 
     * @return il valore di wrappedDTDHandler.
     */
    public DTDHandler getWrappedDTDHandler() {
        return wrappedDTDHandler;
    }
    
    /*
     * <p>Imposta wrappedDTDHandler.</p>
     * 
     * @param wrappedDTDHandler il wrappedDTDHandler da impostare.
     */
    public void setWrappedDTDHandler(DTDHandler wrappedDTDHandler) {
        this.wrappedDTDHandler = wrappedDTDHandler;
    }
    
    private ErrorHandler wrappedErrorHandler;

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    public void error(SAXParseException arg0) throws SAXException {
        this.getWrappedErrorHandler().error(arg0);
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
     */
    public void fatalError(SAXParseException arg0) throws SAXException {
        this.getWrappedErrorHandler().fatalError(arg0);
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
     */
    @Override
    public void warning(SAXParseException arg0) throws SAXException {
        this.getWrappedErrorHandler().warning(arg0);
    }

    /*
     * <p>Restituisce il valore di wrappedHandler.</p>
     * 
     * @return il valore di wrappedHandler.
     */
    public ErrorHandler getWrappedErrorHandler() {
        return wrappedErrorHandler;
    }
    
    /*
     * <p>Imposta wrappedHandler.</p>
     * 
     * @param wrappedHandler il wrappedHandler da impostare.
     */
    public void setWrappedErrorHandler(ErrorHandler wrappedHandler) {
        this.wrappedErrorHandler = wrappedHandler;
    }


}
