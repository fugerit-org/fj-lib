package org.fugerit.java.core.log;

import java.io.IOException;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.slf4j.Logger;

public class LogUtils {

	private LogUtils() {}
	
	public static final String DEF_SEPARATOR = "=";
	
	public static final String DEF_PREFIX = " ";
	
	public static void appendProp( Appendable buffer, String key, String value, String separator, String prefix ) {
		try {
			buffer.append( StringUtils.valueWithDefault( prefix , "") );
			buffer.append( key );
			buffer.append( separator );
			buffer.append( value );			
		} catch (IOException e) {
			throw ConfigRuntimeException.convertExMethod( "appendProp", e );
		}
	}
	
	public static void appendPropDefault( Appendable buffer, String key, String value ) {
		appendProp( buffer, key, value, DEF_SEPARATOR, DEF_PREFIX );
	}
	
	public static void appendProp( StringBuilder buffer, String key, String value, String separator, String prefix ) {
		buffer.append( StringUtils.valueWithDefault( prefix , "") );
		buffer.append( key );
		buffer.append( separator );
		buffer.append( value );
	}
	
	public static void appendPropDefault( StringBuilder buffer, String key, String value ) {
		appendProp( buffer, key, value, DEF_SEPARATOR, DEF_PREFIX );
	}
	
	public static LogObject wrap( final Logger logger ) {
		return () -> logger;
	}
	
}
