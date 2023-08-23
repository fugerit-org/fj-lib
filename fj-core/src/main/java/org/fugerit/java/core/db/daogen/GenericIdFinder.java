package org.fugerit.java.core.db.daogen;

import java.io.IOException;
import java.io.Serializable;

public class GenericIdFinder<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -224608299860106085L;
	
	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	private T id;

	public T getId() {
		return id;
	}

	public void setId( T id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GenericIdFinder [id=" + id + "]";
	}
	
}
