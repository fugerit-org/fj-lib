package org.fugerit.java.core.io;

import java.io.IOException;
import java.io.Serializable;

public class SerialHelper<T> implements Serializable {

	private static final long serialVersionUID = -343550892971766678L;

	public void writeObject( T value, java.io.ObjectOutputStream out) throws IOException {
	}

	public void readObject( T value, java.io.ObjectInputStream in ) throws IOException, ClassNotFoundException {
	}
	
}
