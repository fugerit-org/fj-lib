package org.fugerit.java.core.web.servlet.request;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fugerit.java.core.web.encoder.DefaultSecurityEncoder;
import org.fugerit.java.core.web.encoder.SecurityEncoder;

public class RequestHelper {

	private static final List<String> EMPTY = new ArrayList<String>();
	
	public static final String PARAM_GENERATED = "RequestHelper.GENERATED";
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	public static final String CONST_START = "";
	public static final String CONST_AND = "&";
	
	public static final SecurityEncoder ENCODER = new DefaultSecurityEncoder( DEFAULT_ENCODING );
	
	private static void append( String base, String name, String value, StringBuffer buffer, String encoding, SecurityEncoder encoder ) throws UnsupportedEncodingException {
		buffer.append( base );
		buffer.append( name );
		buffer.append( "=" );
		try {
			buffer.append( encoder.encodeForURL( value ) );
		} catch (Exception e) {
			throw new RuntimeException( "Error while encoding URL" , e );
		}
	}
	
	public static String getParamString( HttpServletRequest requests ) throws UnsupportedEncodingException {
		return getParamString( requests, EMPTY );
	}
	
	public static String getParamString( HttpServletRequest request, List<String> skipParams, SecurityEncoder encoder ) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		append( CONST_START, PARAM_GENERATED, "1", buffer, DEFAULT_ENCODING, encoder );
		Enumeration<String> paramNames = request.getParameterNames();
		while ( paramNames.hasMoreElements() ) {
			String paramName = paramNames.nextElement();
			if ( !skipParams.contains( paramName ) && !paramName.equalsIgnoreCase( PARAM_GENERATED ) ) {
				String[] paramValues = request.getParameterValues( paramName );
				if ( paramValues != null ) {
					for ( int k=0; k<paramValues.length; k++ ) {
						String encoded = paramValues[k];
						append( CONST_AND, paramName, encoded, buffer, DEFAULT_ENCODING, encoder );
					}
				}
			}
		}
		String result = buffer.toString();
		return result;		
	}
	
	public static String getParamString( HttpServletRequest request, List<String> skipParams ) throws UnsupportedEncodingException {	
		return getParamString( request, skipParams, ENCODER );
	}
	
}
