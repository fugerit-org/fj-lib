package org.fugerit.java.core.util.checkpoint;

public class CheckpointUtils {

	public static String formatTimeDiff( long elapsedTime ) {
		return String.valueOf( elapsedTime/1000+"."+elapsedTime%1000+"s" );
	}
	
	public static String formatTimeDiff( long start, long end ) {
		return formatTimeDiff( end-start );
	}
	
	public static String formatTimeDiffMillis( long start, long end ) {
		return String.valueOf(end-start)+" ms";
	}
	
}
