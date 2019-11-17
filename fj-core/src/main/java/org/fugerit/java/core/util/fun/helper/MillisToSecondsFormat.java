package org.fugerit.java.core.util.fun.helper;

import org.fugerit.java.core.util.fun.StringFormat;

public class MillisToSecondsFormat implements StringFormat<Number> {

	public static final StringFormat<Number> DEFAULT = new MillisToSecondsFormat();
	
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
		return builder.toString();
	}

}
