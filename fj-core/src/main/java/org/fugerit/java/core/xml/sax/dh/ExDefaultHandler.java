package org.fugerit.java.core.xml.sax.dh;

import org.fugerit.java.core.lang.helpers.ExHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class ExDefaultHandler extends DefaultHandler {

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    @Override
    public void error(SAXParseException arg0) throws SAXException {
        this.handler.error(arg0);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
     */
    @Override
    public void fatalError(SAXParseException arg0) throws SAXException {
        this.handler.fatal(arg0);
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
     */
    @Override
    public void warning(SAXParseException arg0) throws SAXException {
        this.handler.warning(arg0);
    }
    
    private ExHandler handler;
    
    public ExDefaultHandler(ExHandler handler) {
        this.setHandler(handler);
    }

    /*
     * <p>Restituisce il valore di handler.</p>
     * 
     * @return il valore di handler.
     */
    public ExHandler getHandler() {
        return handler;
    }
    
    /*
     * <p>Imposta handler.</p>
     * 
     * @param handler il handler da impostare.
     */
    public void setHandler(ExHandler handler) {
        this.handler = handler;
    }
    
}
