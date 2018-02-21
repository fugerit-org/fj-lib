package org.fugerit.java.core.xml.sax.eh;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class DoNothingErrorHandler implements ErrorHandler {

    /*
     * <p>Crea un nuovo DoNothingErrorHandler</p>
     * 
     * 
     */
    public DoNothingErrorHandler() {
        super();
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    public void error(SAXParseException arg0) throws SAXException {

    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
     */
    public void fatalError(SAXParseException arg0) throws SAXException {

    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
     */
    public void warning(SAXParseException arg0) throws SAXException {

    }

}
