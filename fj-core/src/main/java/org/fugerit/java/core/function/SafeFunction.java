package org.fugerit.java.core.function;

import java.util.function.Function;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

public class SafeFunction {

	private SafeFunction() {}
	
	public static <T, E extends Exception> T get( UnsafeSupplier<T, Exception> supplier, Function<Exception, E> fun ) throws E {
		T res = null;
		try {
			res = supplier.get();
		} catch (Exception e) {
			throw fun.apply( e );
		}
		return res;
	}
	
	public static <T, E extends Exception> T get( UnsafeSupplier<T, E> supplier ) {
		T res = null;
		try {
			res = supplier.get();
		} catch (Exception e) {
			throw new ConfigRuntimeException( e );
		}
		return res;
	}
	
	public static <E extends Exception> void apply( UnsafeVoid<E> fun ) {
		try {
			fun.apply();
		} catch (Exception e) {
			throw new ConfigRuntimeException( e );
		}
	}
	
}
