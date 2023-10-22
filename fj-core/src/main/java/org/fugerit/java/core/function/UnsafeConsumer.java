package org.fugerit.java.core.function;

public interface UnsafeConsumer<T, E extends Exception> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws E in case of exception
     */
    void accept(T t) throws E;
	
}
