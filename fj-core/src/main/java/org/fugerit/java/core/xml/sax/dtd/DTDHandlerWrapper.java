package org.fugerit.java.core.xml.sax.dtd;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class DTDHandlerWrapper implements DTDHandler {

    public DTDHandlerWrapper(DTDHandler handler) {
        super();
        this.wrappedDTDHandler = handler;
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
    @Override
    public void unparsedEntityDecl(String arg0, String arg1, String arg2,
            String arg3) throws SAXException {
        this.getWrappedDTDHandler().unparsedEntityDecl(arg0, arg1, arg2, arg3);
    }
    
    private DTDHandler wrappedDTDHandler;

    public DTDHandler getWrappedDTDHandler() {
        return wrappedDTDHandler;
    }
    
    public void setWrappedDTDHandler(DTDHandler wrappedDTDHandler) {
        this.wrappedDTDHandler = wrappedDTDHandler;
    }
    
}
