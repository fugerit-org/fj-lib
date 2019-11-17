package org.fugerit.java.core.util.checkpoint;

public interface CheckpointFormat {

	String formatData( CheckpointData data );
	
	String formatTime( long time );
	
	String formatDuration( long duration );

	String tokenStart();
	
	String tokenEnd();
	
	String tokenSeparator();
	
}
