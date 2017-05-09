package org.fugerit.java.core.web.encoder;

import java.io.Serializable;
import java.net.URLEncoder;


public class DefaultSecurityEncoder implements SecurityEncoder, Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -6034157965255717279L;
	
	private String encoding;
	
	public DefaultSecurityEncoder( String encoding ) {
		this.encoding = encoding;
	}
	
	private String encode( String text ) throws EncodingException {
		String result = null;
		try {
			result = URLEncoder.encode( text , this.encoding );
		} catch (Exception e) {
			throw new EncodingException( e );
		}
		return result;
	}
	
	@Override
	public String encodeForCSS(String text) throws EncodingException {
		return this.encode( text );
	}

	@Override
	public String encodeForHTML(String text) throws EncodingException {
		return this.encode( text );
	}

	@Override
	public String encodeForHTMLAttribute(String text) throws EncodingException {
		return this.encode( text );
	}

	@Override
	public String encodeForJavaScript(String text) throws EncodingException {
		return this.encode( text );
	}

	@Override
	public String encodeForURL(String text) throws EncodingException {
		return this.encode( text );
	}

	@Override
	public String encodeForXML(String text) throws EncodingException {
		return this.encode( text );
	}

	@Override
	public String encodeForXMLAttribute(String text) throws EncodingException {
		return this.encode( text );
	}

	

	
}
