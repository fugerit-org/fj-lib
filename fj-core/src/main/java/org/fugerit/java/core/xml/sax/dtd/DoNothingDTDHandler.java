package org.fugerit.java.core.xml.sax.dtd;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class DoNothingDTDHandler implements DTDHandler {
	
	/**
	 * As it is a do nothing implementation, it is safe to use a default (thread safe)
	 */
	public static final DTDHandler DEFAULT = new DoNothingDTDHandler();

    public DoNothingDTDHandler() {
        super();
    }

    /* (non-Javadoc)
     * @see org.xml.sax.DTDHandler#notationDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void notationDecl(String arg0, String arg1, String arg2) throws SAXException {
    	// do nothing implementation
    }

    /* (non-Javadoc)
     * @see org.xml.sax.DTDHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void unparsedEntityDecl(String arg0, String arg1, String arg2, String arg3) throws SAXException {
    	// do nothing implementation
    }

}
