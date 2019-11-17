package org.fugerit.java.core.util.checkpoint;

import java.io.Serializable;

public class CheckpointData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -317299396577387298L;

	private String id;
	
	private long duration;

	private long creationTime;
	
	public String getId() {
		return id;
	}

	public long getDuration() {
		return duration;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public CheckpointData(String id, long duration) {
		super();
		this.id = id;
		this.duration = duration;
		this.creationTime = System.currentTimeMillis();
	}
	
	public String toString() {
		return "[id:"+this.getId()+",time:"+this.getDuration()+"]"; 
	}
}
