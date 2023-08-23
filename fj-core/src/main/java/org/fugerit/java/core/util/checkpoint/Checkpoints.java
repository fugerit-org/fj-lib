package org.fugerit.java.core.util.checkpoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checkpoints implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger( Checkpoints.class );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5332421233105774144L;
	
	private ArrayList<CheckpointData> checkpointsList;

	private Serializable format;
	
	private long startTime;
	
	private long lastCheckpoint;
	
	private Checkpoints( CheckpointFormat format ) {
		this.checkpointsList = new ArrayList<>();
		this.startTime = System.currentTimeMillis();
		this.lastCheckpoint = this.startTime;
		this.format = (Serializable)format;
	}
	
	public CheckpointFormat getFormat() {
		return (CheckpointFormat)format;
	}

	public void setFormat(CheckpointFormat format) {
		this.format = (Serializable)format;
	}

	public void addCheckpoint( String id ) {
		this.addCheckpointFromStartTime( id, this.lastCheckpoint );
	}
	
	public void addCheckpointFromTotalTime( String id, long time ) {
		logger.info( "addCheckpoint id:{},time:{}", id, time );
		this.checkpointsList.add( new CheckpointData( id , time ) );
		this.lastCheckpoint = System.currentTimeMillis();
	}
	
	public void addCheckpointFromStartTime( String id, long time ) {
		this.addCheckpointFromTotalTime( id , System.currentTimeMillis()-time );
	}
	
	public Iterator<CheckpointData> checkpointIterator() {
		return this.checkpointsList.iterator();
	}

	private String toStringHelper( boolean pretty ) {
		StringBuilder builder = new StringBuilder();
		builder.append( this.getClass().getSimpleName() );
		long previousTime = this.startTime;
		if ( pretty ) {
			builder.append( " prettyPrintinfo : " );
			builder.append( '\n' );
		}
		for ( CheckpointData current : this.checkpointsList ) {
			builder.append( this.getFormat().formatData( current ) );
			previousTime = current.getCreationTime();
			if ( pretty ) {
				builder.append( '\n' );
			}
		}
		builder.append( this.getFormat().tokenStart() );
		if ( pretty ) {
			builder.append( "totalDuration:" );
		}
		builder.append( this.getFormat().formatDuration( ( previousTime-this.startTime ) ) );
		builder.append( this.getFormat().tokenEnd() );
		if ( pretty ) {
			builder.append( '\n' );
		}
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return this.toStringHelper( false );
	}
	
	public void printInfo() {
		String info = this.toStringHelper( false );
		logger.info( info );
	}
	
	public void prettyPrintInfo() {
		String info = this.toStringHelper( true );
		logger.info( info );
	}
	
	public static Checkpoints newInstance( CheckpointFormat format ) {
		return new Checkpoints( format );
	}
	
	public static Checkpoints newInstance() {
		return new Checkpoints( CheckpointFormatHelper.DEFAULT );
	}

	public long getStartTime() {
		return startTime;
	}

	public long getLastCheckpoint() {
		return lastCheckpoint;
	}
	
}

