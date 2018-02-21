package org.fugerit.java.core.xml.sax.er;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class ByteArrayEntityResolver implements EntityResolver {

    private String publicID;
    private String systemID;
    private byte[] entity;
    
    public ByteArrayEntityResolver(byte[] entity, String publicID, String systemID ) {
        super();
        this.publicID = publicID;
        this.systemID = systemID;
        this.entity = entity;
    }

    /* (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity(String arg0, String arg1)
            throws SAXException, IOException {
        InputSource source = null;
        if ((arg0!=null && arg0.equals(this.publicID)) || (arg1!=null && arg1.equals(this.systemID))) {
            source = new InputSource(new ByteArrayInputStream(this.entity));
        }
        return source;
    }

}
