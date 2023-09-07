package org.fugerit.java.core.function;

public interface UnsafeVoid<E extends Exception> {

	void apply() throws E;
	
}
