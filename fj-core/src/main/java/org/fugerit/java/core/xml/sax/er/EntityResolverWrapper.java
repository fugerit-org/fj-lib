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
public class EntityResolverWrapper implements EntityResolver {

    public EntityResolverWrapper(EntityResolver resolver) {
        super();
        this.wrappedEntityResolver = resolver;
    }

    /* (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException {
        return this.getWrappedEntityResolver().resolveEntity(arg0, arg1);
    }
    
    private EntityResolver wrappedEntityResolver;

    /*
     * <p>Restituisce il valore di wrappedEntityResolver.</p>
     * 
     * @return il valore di wrappedEntityResolver.
     */
    public EntityResolver getWrappedEntityResolver() {
        return wrappedEntityResolver;
    }
    
    /*
     * <p>Imposta wrappedEntityResolver.</p>
     * 
     * @param wrappedEntityResolver il wrappedEntityResolver da impostare.
     */
    public void setWrappedEntityResolver(EntityResolver wrappedEntityResolver) {
        this.wrappedEntityResolver = wrappedEntityResolver;
    }
    
}
