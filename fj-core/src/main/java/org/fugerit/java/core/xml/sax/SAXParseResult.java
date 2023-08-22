package org.fugerit.java.core.xml.sax;

import org.fugerit.java.core.lang.helpers.Result;
import org.xml.sax.SAXParseException;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public class SAXParseResult extends Result {

    /**
	 * 
	 */
	private static final long serialVersionUID = -48185225120198807L;

	/*
     * <p>Crea un nuovo SAXParseResult</p>
     * 
     * 
     */
    public SAXParseResult() {
        super();
    }

    public SAXParseException getSAXError(int index) {
        return (SAXParseException)this.getError(index);
    }
    
    public SAXParseException getSAXFatal(int index) {
        return (SAXParseException)this.getFatal(index);
    }    
    
    public SAXParseException getSAXWarning(int index) {
        return (SAXParseException)this.getWarning(index);
    }

}
