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

	/**
	 * As it is a do nothing implementation, it is safe to use a default (thread safe)
	 */
	public static final ErrorHandler DEFAULT = new DoNothingErrorHandler();
	
	public DoNothingErrorHandler() {
        super();
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    @Override
    public void error(SAXParseException arg0) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
     */
    @Override
    public void fatalError(SAXParseException arg0) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
     */
    @Override
    public void warning(SAXParseException arg0) throws SAXException {
    	// do nothing implementation
    }

}
