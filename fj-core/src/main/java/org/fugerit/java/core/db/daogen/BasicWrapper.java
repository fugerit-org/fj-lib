package org.fugerit.java.core.db.daogen;

public class BasicWrapper<T> extends BasicHelper {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2800606962171545339L;
	
	private T wrapped;

	public T unwrapModel() {
		return wrapped;
	}

	public void wrapModel(T wrapped) {
		this.wrapped = wrapped;
	}

	public BasicWrapper(T wrapped) {
		super();
		this.wrapped = wrapped;
	}

	public String toString() {
		return this.getClass().getSimpleName()+"[wraps:"+unwrapModel().toString()+"]";
	}

	@SuppressWarnings("unchecked")
	public T unwrap() {
		T res = this.unwrapModel();
		while ( res instanceof BasicWrapper ) {
			res = ((BasicWrapper<T>)res).unwrapModel();
		}
		return res;
	}

}