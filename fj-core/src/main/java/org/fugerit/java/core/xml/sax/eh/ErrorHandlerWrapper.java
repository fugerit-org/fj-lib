package org.fugerit.java.core.xml.sax.eh;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class ErrorHandlerWrapper implements ErrorHandler {
    
    /*
     * <p>Crea un nuovo ErrorHandlerWrapper</p>
     * 
     * 
     */
    public ErrorHandlerWrapper(ErrorHandler handler) {
        super();
        this.wrappedErrorHandler = handler;
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
    public void warning(SAXParseException arg0) throws SAXException {
        this.getWrappedErrorHandler().warning(arg0);
    }

    public ErrorHandler getWrappedErrorHandler() {
        return wrappedErrorHandler;
    }
    
    public void setWrappedErrorHandler(ErrorHandler wrappedHandler) {
        this.wrappedErrorHandler = wrappedHandler;
    }
    
}
