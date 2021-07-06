package org.fugerit.java.core.xml.sax.er;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class DoNothingEntityResolver implements EntityResolver {

    /*
     * <p>Crea un nuovo DoNothingEntityResolver</p>
     * 
     * 
     */
    public DoNothingEntityResolver() {
        super();
    }

    /* (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity(String arg0, String arg1)
            throws SAXException, IOException {
        return null;
    }

}
