package org.fugerit.java.core.function;

public interface UnsafeSupplier<T, E extends Exception> {

	T get() throws E;
	
}
