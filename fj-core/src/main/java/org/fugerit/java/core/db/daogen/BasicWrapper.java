package org.fugerit.java.core.db.daogen;

import java.io.Serializable;

import org.fugerit.java.core.lang.helpers.Wrapper;
import org.fugerit.java.core.lang.helpers.WrapperHelper;

public class BasicWrapper<T extends Serializable> extends BasicHelper implements Wrapper<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2800606962171545339L;
	
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
