package org.fugerit.java.core.function;

import java.util.function.Consumer;
import java.util.function.Function;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

/**
 * <p>Collections of utilities for handling exceptions through functions.</p>
 * 
 * <p>For instance, if the goal is to read an input without having the method throw an exception, a typical approach could be :</p>
 *
 * <pre>{@code
  	public String testExampleToOneLineClassic() {
		StringBuilder builder = new StringBuilder();
		try ( BufferedReader reader = new BufferedReader( new StringReader( "test" ) ) ) {
			reader.lines().forEach( line -> builder.append( line+" " ) );
		} catch (IOException e) {
			throw new ConfigRuntimeException( e );
		}
		return builder.toString();
	}}</pre>
	
 * <p>With SafeFunction.get() the same code could be written as follow :</p>
	
   <pre>{@code
	public String testExampleToOneLineSafeFunction() {
		return SafeFunction.get(() -> {
			StringBuilder builder = new StringBuilder();
			try ( BufferedReader reader = new BufferedReader( new StringReader( "test" ) ) ) {
				reader.lines().forEach( line -> builder.append( line+" " ) );
			}	
			return builder.toString();
		});
	}}</pre>
 * 
 * <p>So this way it is possible to separate the exception handling from the actual software logic. (useful to reduce testing coverage too).</p>
 * 
 */
public class SafeFunction {

	private SafeFunction() {}
	
	private static final Consumer<Exception> DEFAULT_EX_CONSUMER = e -> { throw new ConfigRuntimeException( e ); };
	
	/**
	 * <p>Get a value returned by an UnsafeSupplier, and convert any raised Exception</p>
	 * 
	 * @param <T>		the returned type
	 * @param <E>		the exception type
	 * @param supplier	the {@link UnsafeSupplier} function
	 * @param fun		the Exception conversion function
	 * @return			the value evaluated by the {@link UnsafeSupplier} 
	 * @throws E		the type of the raised Exception
	 */
	public static <T, E extends Exception> T getEx( UnsafeSupplier<T, Exception> supplier, Function<Exception, E> fun ) throws E {
		T res = null;
		try {
			res = supplier.get();
		} catch (Exception e) {
			throw fun.apply( e );
		}
		return res;
	}
	
	/**
	 * <p>Return the value of an UnsafeSupplier, converting any raised Exception to ConfigRuntimeException.</p>
	 * 
	 * @param <T>			the returned type
	 * @param supplier		the {@link UnsafeSupplier}  function
	 * @return				the value evaluated by the {@link UnsafeSupplier} 
	 * @throws ConfigRuntimeException 	may throw ConfigRuntimeException in case of exceptions
	 */
	public static <T> T get( UnsafeSupplier<T, Exception> supplier ) {
		return get( supplier, DEFAULT_EX_CONSUMER );
	}
	
	/**
	 * <p>Apply an UnsafeVoid function, converting any raised Exception to ConfigRuntimeException.</p>
	 * 
	 * @param fun	the {@link UnsafeVoid} function
	 */
	public static void apply( UnsafeVoid<Exception> fun ) {
		apply( fun , DEFAULT_EX_CONSUMER );
	}
	
	/**
	 * <p>Return the value provided by the supplier, handling any exception with a {@link Consumer} function.</p>
	 * 
	 * @param <T>			the return type
	 * @param supplier		the {@link UnsafeSupplier} the return value supplier
	 * @param exHandler		the exception handler
	 * @return				the value evaluated by the supplier
	 */
	public static <T> T get( UnsafeSupplier<T, Exception> supplier, Consumer<Exception> exHandler ) {
		return getWithDefault( supplier, e -> { exHandler.accept(e); return null; }  );
	}
	
	/**
	 * <p>Return the value provided by the supplier, handling any exception with a {@link Consumer} function.</p>
	 * 
	 * @param <T>			the return type
	 * @param supplier		the {@link UnsafeSupplier} the return value supplier
	 * @param exHandler		the exception handler as a function that can provide a default value
	 * @return				the value evaluated by the supplier
	 */
	public static <T> T getWithDefault( UnsafeSupplier<T, Exception> supplier, Function<Exception, T> exHandler ) {
		T res = null;
		try {
			res = supplier.get();
		} catch (Exception e) {
			res = exHandler.apply( e );
		}
		return res;
	}
	
	/**
	 * <p>Apply an UnsafeVoid function, using a consumer the handle any Exception raised.</p>
	 * 
	 * @param fun			the {@link UnsafeVoid} function
	 * @param exHandler		the consumer to handle the exception
	 */
	public static void apply( UnsafeVoid<Exception> fun, Consumer<Exception> exHandler ) {
		try {
			fun.apply();
		} catch (Exception e) {
			exHandler.accept( e );
		}
	}

	public static boolean applyOnCondition( UnsafeSupplier<Boolean, Exception> suppplier, UnsafeVoid<Exception> fun ) {
		boolean cond = get( suppplier );
		if ( cond ) {
			apply( fun );
		}
		return cond;
	}
	
	public static <T> boolean applyIfNotNull( T v, UnsafeVoid<Exception> fun ) {
		return applyOnCondition( () -> v != null , fun);
	}
	
}
