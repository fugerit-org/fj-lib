package org.fugerit.java.core.validator;

import org.fugerit.java.core.lang.helpers.Wrapper;

public class ValidatorWrapper extends BasicValidator implements Wrapper<BasicValidator> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2745412297380349295L;

	private BasicValidator wrapped;
	
	public ValidatorWrapper( BasicValidator wrapped ) {
		this.wrapped = wrapped;
	}

	@Override
	public BasicValidator unwrapModel() {
		return this.wrapped;
	}

	@Override
	public void wrapModel(BasicValidator wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public BasicValidator unwrap() {
		BasicValidator res = this.unwrapModel();
		while ( res instanceof ValidatorWrapper ) {
			res = ((ValidatorWrapper)res).unwrapModel();
		}
		return res;
	}
	
}
