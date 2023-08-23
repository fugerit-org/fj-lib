package test.org.fugerit.java.core.basic.serialization;

import java.io.Serializable;

/**
 * Simple not {@link Serializable} class for test purposes
 */
public class NotSerializableID {

	public static NotSerializableID valueOf( long value ) {
		return new NotSerializableID(value);
	}
	
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public NotSerializableID(long id) {
		super();
		this.id = id;
	}
	
	public long longValue() {
		return this.getId();
	}
	
}
