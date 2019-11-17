package org.fugerit.java.core.util.fun.helper;

import java.io.Serializable;

import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.fun.StringFormat;

public class MillisToSecondsFormat implements StringFormat<Number>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8394397738041690751L;

	public static final String APPEND_SECOND = "s";
	
	public static final String APPEND_NOTHING = "";
	
	public static final StringFormat<Number> INSTANCE_APPEND_SECOND = new MillisToSecondsFormat( APPEND_SECOND );
	
	public static final StringFormat<Number> INSTANCE_APPEND_NOTHING = new MillisToSecondsFormat( APPEND_NOTHING );
	
	public static final StringFormat<Number> DEFAULT = INSTANCE_APPEND_SECOND;
	
	private String append;
	
	public MillisToSecondsFormat(String append) {
		super();
		this.append = append;
	}
	
	public MillisToSecondsFormat() {
		this( APPEND_NOTHING );
	}
	
	public String getAppend() {
		return append;
	}

	@Override
	public String convert(Number input) {
		long time = input.longValue();
		StringBuilder builder = new StringBuilder();
		builder.append( (time/1000) );
		builder.append( "." );
		String millis = String.valueOf( (time%1000) );
		while ( millis.length() < 3 ) {
			millis = "0"+millis;
		}
		builder.append( millis );
		if ( StringUtils.isNotEmpty( this.getAppend() ) ) {
			builder.append( this.getAppend() );	
		}
		return builder.toString();
	}

}
