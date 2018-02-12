package org.fugerit.java.core.xml.sax.dtd;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class DoNothingDTDHandler implements DTDHandler {

    /*
     * <p>Crea un nuovo DoNothingDTDHandler</p>
     * 
     * 
     */
    public DoNothingDTDHandler() {
        super();
    }

    /* (non-Javadoc)
     * @see org.xml.sax.DTDHandler#notationDecl(java.lang.String, java.lang.String, java.lang.String)
     */
    public void notationDecl(String arg0, String arg1, String arg2)
            throws SAXException {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.DTDHandler#unparsedEntityDecl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void unparsedEntityDecl(String arg0, String arg1, String arg2,
            String arg3) throws SAXException {
    }

}
