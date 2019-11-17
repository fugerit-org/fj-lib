package org.fugerit.java.core.util.checkpoint;

public class CheckpointFormatWrapper extends CheckpointFormatHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -838210692334232041L;

	private CheckpointFormat wrapped;
	
	public CheckpointFormatWrapper(CheckpointFormat wrapped) {
		super();
		this.wrapped = wrapped;
	}

	public CheckpointFormat unwrap() {
		return this.wrapped;
	}
	
	@Override
	public String formatData(CheckpointData data) {
		return this.unwrap().formatData(data);
	}

	@Override
	public String formatTime(long time) {
		return this.unwrap().formatTime(time);
	}

	@Override
	public String formatDuration(long duration) {
		return this.unwrap().formatDuration(duration);
	}

	@Override
	public String tokenStart() {
		return this.unwrap().tokenStart();
	}

	@Override
	public String tokenEnd() {
		return this.unwrap().tokenEnd();
	}

	@Override
	public String tokenSeparator() {
		return this.unwrap().tokenSeparator();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[wrap:"+this.unwrap()+"]";
	}

}
