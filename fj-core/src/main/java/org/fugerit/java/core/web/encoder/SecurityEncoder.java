package org.fugerit.java.core.web.encoder;

public interface SecurityEncoder {

	public String encodeForCSS( String text ) throws EncodingException;
	
	public String encodeForHTML( String text ) throws EncodingException;
	
	public String encodeForHTMLAttribute( String text ) throws EncodingException;
	
	public String encodeForJavaScript( String text ) throws EncodingException;
	
	public String encodeForURL( String text ) throws EncodingException;
	
	public String encodeForXML( String text ) throws EncodingException;
	
	public String encodeForXMLAttribute( String text ) throws EncodingException;
	
	
}
