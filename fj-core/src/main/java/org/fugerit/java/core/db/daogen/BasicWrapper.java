package org.fugerit.java.core.db.daogen;

import java.io.IOException;
import java.io.Serializable;

import org.fugerit.java.core.lang.helpers.Wrapper;
import org.fugerit.java.core.lang.helpers.WrapperHelper;

public class BasicWrapper<T> implements Wrapper<T>, Serializable {

	private static final long serialVersionUID = -9201341852062067118L;
	
	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	protected static final boolean UNSOPPORTED_OPERATION = true;
	
	public static void throwUnsupported( String message ) {
		if ( UNSOPPORTED_OPERATION ) {
			throw new UnsupportedOperationException( message );
		}
	}
	
	private T wrapped;

	@Override
	public T unwrapModel() {
		return wrapped;
	}

	@Override
	public void wrapModel(T wrapped) {
		this.wrapped = wrapped;
	}

	public BasicWrapper(T wrapped) {
		super();
		this.wrapped = wrapped;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[wraps:"+unwrapModel().toString()+"]";
	}

	@Override
	public T unwrap() {
		return WrapperHelper.unwrap( this.wrapped );
	}

}
