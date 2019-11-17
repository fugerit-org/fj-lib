package org.fugerit.java.core.util.fun.helper;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fugerit.java.core.util.format.TimeFormatDefault;
import org.fugerit.java.core.util.fun.StringFormat;

public class FormatTime implements StringFormat<Number>, Serializable {

	public static final FormatTime INSTANCE_ISO_TZ = new FormatTime( TimeFormatDefault.TIMESTAMP_ISO_TZ );
	
	public static final FormatTime DEFAULT = INSTANCE_ISO_TZ;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7043852329294349137L;
	
	private String formatString;
		
	public String getFormatString() {
		return formatString;
	}

	public FormatTime(String formatString) {
		super();
		this.formatString = formatString;
	}

	public DateFormat newDateFormat() {
		return new SimpleDateFormat( this.getFormatString() );
	}

	@Override
	public String convert(Number input) {
		return this.newDateFormat().format( new Date( input.longValue() ) );
	}

}
