package org.fugerit.java.core.util.checkpoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Checkpoints implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger( Checkpoints.class );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5332421233105774144L;
	
	private Collection<CheckpointData> checkpoints;

	private CheckpointFormat format;
	
	private long startTime;
	
	private long lastCheckpoint;
	
	private Checkpoints( CheckpointFormat format ) {
		this.checkpoints = new ArrayList<>();
		this.startTime = System.currentTimeMillis();
		this.lastCheckpoint = this.startTime;
		this.format = format;
	}
	
	public CheckpointFormat getFormat() {
		return format;
	}

	public void setFormat(CheckpointFormat format) {
		this.format = format;
	}

	public void addCheckpoint( String id ) {
		this.addCheckpointFromStartTime( id, this.lastCheckpoint );
	}
	
	public void addCheckpointFromTotalTime( String id, long time ) {
		logger.info( "addCheckpoint id:{},time:{}", id, time );
		this.checkpoints.add( new CheckpointData( id , time ) );
		this.lastCheckpoint = System.currentTimeMillis();
	}
	
	public void addCheckpointFromStartTime( String id, long time ) {
		this.addCheckpointFromTotalTime( id , System.currentTimeMillis()-time );
	}
	
	public Iterator<CheckpointData> checkpointIterator() {
		return this.checkpoints.iterator();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( this.getClass().getSimpleName() );
		long previousTime = this.startTime;
		logger.info( "FORMAT:"+this.format );  
		for ( CheckpointData current : this.checkpoints ) {
			builder.append( this.format.formatData( current ) );
			previousTime = current.getCreationTime();
		}
		builder.append( this.format.tokenStart() );
		builder.append( this.format.formatDuration( ( previousTime-this.startTime ) ) );
		builder.append( this.format.tokenEnd() );
		return builder.toString();
	}
	
	public void printInfo() {
		logger.info( this.toString() );
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

