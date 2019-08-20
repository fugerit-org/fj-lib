package org.fugerit.java.core.log;

import org.fugerit.java.core.lang.helpers.StringUtils;
import org.slf4j.Logger;

public class LogUtils {

	public static final String DEF_SEPARATOR = "=";
	

	public static final String DEF_PREFIX = " ";
	
	public static void appendProp( StringBuffer buffer, String key, String value, String separator, String prefix ) {
		buffer.append( StringUtils.valueWithDefault( prefix , "") );
		buffer.append( key );
		buffer.append( separator );
		buffer.append( value );
	}
	
	public static void appendPropDefault( StringBuffer buffer, String key, String value ) {
		appendProp( buffer, key, value, DEF_SEPARATOR, DEF_PREFIX );
	}
	
	public static LogObject wrap( final Logger logger ) {
		return new LogObject() {
			@Override
			public Logger getLogger() {
				return logger;
			}
		};
	}
	
}
