package org.fugerit.java.core.xml.helpers;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.XMLValidator;
import org.fugerit.java.core.xml.sax.SAXParseResult;
import org.xml.sax.InputSource;

/*
 * 
 * 
 * @author  Matteo Franci a.k.a. Fugerit
 */
public abstract class AbstractXMLValidator implements XMLValidator {

    public boolean isValid(SAXParseResult result) {
        return result.isTotalSuccess();
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(org.xml.sax.InputSource, org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public abstract boolean validateXML(InputSource source, SAXParseResult result) throws XMLException;

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(org.xml.sax.InputSource)
     */
    @Override
    public SAXParseResult validateXML(InputSource source) throws XMLException {
        SAXParseResult result = new SAXParseResult();
        this.validateXML(source, result);
        return result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#isValidaXML(org.xml.sax.InputSource)
     */
    @Override
    public boolean isValidaXML(InputSource source) throws XMLException {
        return this.isValid(this.validateXML(source));
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.io.Reader, org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public boolean validateXML(Reader source, SAXParseResult result)
            throws XMLException {
        return this.validateXML(new InputSource(source), result);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.io.Reader)
     */
    @Override
    public SAXParseResult validateXML(Reader source) throws XMLException {
        SAXParseResult result = new SAXParseResult();
        this.validateXML(source, result);
        return result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#isValidaXML(java.io.Reader)
     */
    @Override
    public boolean isValidaXML(Reader source) throws XMLException {
        return this.isValid(this.validateXML(source));
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.io.InputStream, org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public boolean validateXML(InputStream source, SAXParseResult result)
            throws XMLException {
        return this.validateXML(new InputSource(source), result);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.io.InputStream)
     */
    @Override
    public SAXParseResult validateXML(InputStream source) throws XMLException {
        SAXParseResult result = new SAXParseResult();
        this.validateXML(source, result);
        return result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#isValidaXML(java.io.InputStream)
     */
    @Override
    public boolean isValidaXML(InputStream source) throws XMLException {
        return this.isValid(this.validateXML(source));
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.lang.StringBuffer, org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public boolean validateXML(StringBuffer source, SAXParseResult result) throws XMLException {
        return this.validateXML(source.toString(), result);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.lang.StringBuffer)
     */
    @Override
    public SAXParseResult validateXML(StringBuffer source) throws XMLException {
        SAXParseResult result = new SAXParseResult();
        this.validateXML(source, result);
        return result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#isValidaXML(java.lang.StringBuffer)
     */
    @Override
    public boolean isValidaXML(StringBuffer source) throws XMLException {
        return this.isValid(this.validateXML(source));
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.lang.String, org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public boolean validateXML(String source, SAXParseResult result)
            throws XMLException {
        return this.validateXML(new StringReader(source), result);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(java.lang.String)
     */
    @Override
    public SAXParseResult validateXML(String source) throws XMLException {
        SAXParseResult result = new SAXParseResult();
        this.validateXML(source, result);
        return result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#isValidaXML(java.lang.String)
     */
    @Override
    public boolean isValidaXML(String source) throws XMLException {
        return this.isValid(this.validateXML(source));
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(byte[], org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public boolean validateXML(byte[] source, SAXParseResult result)
            throws XMLException {
        return this.validateXML(new ByteArrayInputStream(source), result);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(byte[])
     */
    @Override
    public SAXParseResult validateXML(byte[] source) throws XMLException {
        SAXParseResult result = new SAXParseResult();
        this.validateXML(source, result);
        return result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#isValidaXML(byte[])
     */
    @Override
    public boolean isValidaXML(byte[] source) throws XMLException {
        return this.isValid(this.validateXML(source));
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(char[], org.fugerit.java.core.xml.sax.SAXParseResult)
     */
    @Override
    public boolean validateXML(char[] source, SAXParseResult result)
            throws XMLException {
        return this.validateXML(new CharArrayReader(source), result);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#validateXML(char[])
     */
    @Override
    public SAXParseResult validateXML(char[] source) throws XMLException {
        SAXParseResult result = new SAXParseResult();
        this.validateXML(source, result);
        return result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.xml.XMLValidator#isValidaXML(char[])
     */
    @Override
    public boolean isValidaXML(char[] source) throws XMLException {
        return this.isValid(this.validateXML(source));
    }

}
