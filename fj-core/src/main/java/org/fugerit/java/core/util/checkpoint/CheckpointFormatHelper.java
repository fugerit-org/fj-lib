package org.fugerit.java.core.util.checkpoint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fugerit.java.core.util.format.TimeFormatDefault;
import org.fugerit.java.core.util.fun.StringFormat;
import org.fugerit.java.core.util.fun.helper.MillisToSecondsFormat;

public class CheckpointFormatHelper implements CheckpointFormat, Serializable {
	
	public static final CheckpointFormat DEFAULT = new CheckpointFormatHelper();
	
	public static final CheckpointFormat DEFAULT_DECORATION = decorateDefault( DEFAULT );
	
	public final static String TOKEN_START_DEF = "[";
	public final static String TOKEN_END_DEF = "]";
	public final static String TOKEN_SEPARATOR_DEF = ",";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 134289656334324324L;

	public static void formatDataHelperDefault( CheckpointFormat format, StringBuilder builder, CheckpointData data ) {
		builder.append( format.tokenStart() );
		builder.append( data.getId() );
		builder.append( format.tokenSeparator() );
		builder.append( "endTime:" );
		builder.append( format.formatTime( data.getCreationTime() ) );
		builder.append( format.tokenSeparator() );
		builder.append( "duration:" );
		builder.append( format.formatDuration( data.getDuration() ) );
		builder.append( "m" );
		builder.append( format.tokenEnd() );
	}
	
	@Override
	public String formatData(CheckpointData data) {
		StringBuilder builder = new StringBuilder();
		formatDataHelperDefault( this, builder , data );
		return builder.toString();
	}

	@Override
	public String formatTime(long time) {
		return String.valueOf( time );
	}

	@Override
	public String formatDuration(long duration) {
		return String.valueOf( duration );
	}

	@Override
	public String tokenStart() {
		return TOKEN_START_DEF;
	}

	@Override
	public String tokenEnd() {
		return TOKEN_END_DEF;
	}

	@Override
	public String tokenSeparator() {
		return TOKEN_SEPARATOR_DEF;
	}

	public static CheckpointFormat decorateDefault( final CheckpointFormat format ) {
		return decorateFormatTimeDefault( decorateFormatDurationDefault( format ) );
	}
	
	public static CheckpointFormat decorateFormatTimeDefault( final CheckpointFormat format ) {
		return decorateFormatTime( format, TimeFormatDefault.TIMESTAMP_ISO_TZ );
	}
	
	public static CheckpointFormat decorateFormatDurationDefault( final CheckpointFormat format ) {
		return decorateFormatDuration( format, MillisToSecondsFormat.DEFAULT );
	}
	
	public static CheckpointFormat decorateFormatTime( final CheckpointFormat format, final String timeFormat ) {
		return new TimeDecorator(format,timeFormat);
	}
	
	public static CheckpointFormat decorateFormatDuration( final CheckpointFormat format, final StringFormat<Number> durationFormat ) {
		return new DurationDecorator(format,durationFormat);
	}
	
}

class BasicDecorator extends CheckpointFormatWrapper {

	public BasicDecorator(CheckpointFormat wrapped) {
		super(wrapped);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1872391687939666192L;
	
	@Override
	public String formatData(CheckpointData data) {
		StringBuilder builder = new StringBuilder();
		formatDataHelperDefault( this, builder , data );
		return builder.toString();
	}
	
}

class TimeDecorator extends BasicDecorator {
	
	private String timeFormat;
	
	public TimeDecorator(CheckpointFormat wrapped, String timeFormat) {
		super(wrapped);	
		this.timeFormat = timeFormat;
	}

	private static final long serialVersionUID = 6303015532097392362L;
	
	@Override
	public String formatTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat( this.timeFormat );
		return sdf.format( new Date( time ) );
	}
	
}

class DurationDecorator extends BasicDecorator {
	
	private StringFormat<Number> durationFormat;
	
	public DurationDecorator(CheckpointFormat wrapped, StringFormat<Number> durationFormat) {
		super(wrapped);	
		this.durationFormat = durationFormat;
	}

	private static final long serialVersionUID = 6303015532097392362L;
	
	@Override
	public String formatDuration(long duration) {
		return this.durationFormat.convert( duration );
	}
	
}


