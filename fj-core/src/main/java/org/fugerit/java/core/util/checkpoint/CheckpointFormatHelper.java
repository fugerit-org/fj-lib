package org.fugerit.java.core.util.checkpoint;

import java.io.Serializable;

import org.fugerit.java.core.util.fun.StringFormat;
import org.fugerit.java.core.util.fun.helper.FormatTime;
import org.fugerit.java.core.util.fun.helper.MillisToSecondsFormat;

public class CheckpointFormatHelper implements CheckpointFormat, Serializable {
	
	private static final long serialVersionUID = -3532044603092080930L;

	public static final StringFormat<Number> FORMAT_TIME_DEFAULT = new StringFormat<Number>() {
		@Override
		public String convert(Number input) {
			return String.valueOf( input.longValue() );
		}
	};
	
	public static final StringFormat<Number> FORMAT_TIME_NICE = FormatTime.DEFAULT;
	
	public static final StringFormat<Number> FORMAT_DURATION_DEFAULT = new StringFormat<Number>() {
		@Override
		public String convert(Number input) {
			return String.valueOf( input.longValue() )+"ms";
		}
	};
	
	public static final StringFormat<Number> FORMAT_DURATION_NICE = MillisToSecondsFormat.INSTANCE_APPEND_SECOND;
	
	public static final CheckpointFormat DEFAULT = new CheckpointFormatHelper( FORMAT_TIME_DEFAULT, FORMAT_DURATION_DEFAULT );
	
	public static final CheckpointFormat DEFAULT_DECORATION = new CheckpointFormatHelper( FORMAT_TIME_NICE , FORMAT_DURATION_NICE );
	
	public final static String TOKEN_START_DEF = "[";
	public final static String TOKEN_END_DEF = "]";
	public final static String TOKEN_SEPARATOR_DEF = ",";

	public void formatDataHelperDefault( StringBuilder builder, CheckpointData data ) {
		builder.append( this.tokenStart() );
		builder.append( data.getId() );
		builder.append( this.tokenSeparator() );
		builder.append( "endTime:" );
		builder.append( this.formatTime( data.getCreationTime() ) );
		builder.append( this.tokenSeparator() );
		builder.append( "duration:" );
		builder.append( this.formatDuration( data.getDuration() ) );
		builder.append( this.tokenEnd() );
	}
	
	@Override
	public String formatData(CheckpointData data) {
		StringBuilder builder = new StringBuilder();
		formatDataHelperDefault( builder , data );
		return builder.toString();
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

	private StringFormat<Number> formatTime;
	
	private StringFormat<Number> formatDuration;
	
	public StringFormat<Number>  getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(StringFormat<Number> formatTime) {
		this.formatTime = formatTime;
	}

	public StringFormat<Number> getFormatDuration() {
		return formatDuration;
	}

	public void setFormatDuration(StringFormat<Number> formatDuration) {
		this.formatDuration = formatDuration;
	}

	public CheckpointFormatHelper() {
		this( FORMAT_TIME_DEFAULT, FORMAT_DURATION_DEFAULT );
	}
	
	public CheckpointFormatHelper(StringFormat<Number> formatTime, StringFormat<Number> formatDuration) {
		super();
		this.formatTime = formatTime;
		this.formatDuration = formatDuration;
	}

	@Override
	public String formatTime(long time) {
		return this.getFormatTime().convert( time );
	}

	@Override
	public String formatDuration(long duration) {
		return this.getFormatDuration().convert( duration );
	}
	
	public static CheckpointFormat newInstance( StringFormat<Number> formatTime, StringFormat<Number> formatDuration ) {
		return new CheckpointFormatHelper(formatTime, formatDuration);
	}
	
}
