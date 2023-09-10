package org.fugerit.java.core.xml.sax.eh;

import org.fugerit.java.core.lang.helpers.ExHandler;
import org.fugerit.java.core.lang.helpers.Result;
import org.fugerit.java.core.lang.helpers.ResultExHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class ResultErrorHandler implements ErrorHandler {
    
    /*
     * <p>Crea un nuovo ResultErrorHandler</p>
     * 
     * 
     */
    public ResultErrorHandler(Result res) {
        super();
        this.handler = new ResultExHandler(res);
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
    
}
