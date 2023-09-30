package org.fugerit.java.core.function;

import java.util.function.Consumer;
import java.util.function.Function;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class SafeFunction {

	private SafeFunction() {}
	
	/**
	 * <p>This Exception consumer just log the exception at warn log level. (no Exception is re-thrown).</p>
	 */
	public static final Consumer<Exception> EX_CONSUMER_LOG_WARN = e -> log.warn( "Exception suppressed : {}", e.toString() );
	
	/**
	 * <p>This Exception consumer log and print the stack trace of the exception at warn log level. (no Exception is re-thrown).</p>
	 */
	public static final Consumer<Exception> EX_CONSUMER_TRACE_WARN = e -> log.warn( "Exception suppressed : "+e, e );
	
	/**
	 * <p>This Exception consumer wraps any Exception around a {@link ConfigRuntimeException}.</p>
	 */
	public static final Consumer<Exception> EX_CONSUMER_THROW_CONFIG_RUNTIME = e -> { throw new ConfigRuntimeException( e ); };
	
	/**
	 * <p>This Exception consumer wraps checked Exception around a {@link ConfigRuntimeException}.</p>
	 * 
	 * <p>RuntimeException are just re-thrown.</p>
	 */
	public static final Consumer<Exception> EX_CONSUMER_RETHROW_RTE_OR_CONVERT_CHECKED_TO_CRE = e -> { 
		if ( e instanceof RuntimeException ) {
			throw (RuntimeException)e;
		} else {
			throw new ConfigRuntimeException( "Convert exception to ConfigRuntimeException : "+e, e );
		}
	};
	
	/**
	 * Default behavior is {@link EX_CONSUMER_RETHROW_RTE_OR_CONVERT_CHECKED_TO_CRE}
	 */
	private static final Consumer<Exception> DEFAULT_EX_CONSUMER = EX_CONSUMER_RETHROW_RTE_OR_CONVERT_CHECKED_TO_CRE;
	
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

	public static void applySilent( UnsafeVoid<Exception> fun ) {
		apply( fun , EX_CONSUMER_LOG_WARN );
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

	/**
	 * <p>This method will apply a function only if a condition is met.</p>
	 * 
	 * @param condition		the condition to check
	 * @param fun			the function to apply if the condition returned <code>true</code>
	 * @return				the value returned by the condition
	 */
	public static boolean applyOnCondition( UnsafeSupplier<Boolean, Exception> condition, UnsafeVoid<Exception> fun ) {
		boolean cond = get( condition );
		if ( cond ) {
			apply( fun );
		}
		return cond;
	}
	
	/**
	 * <p>This method will apply a function only if a object is not null.</p>
	 * 
	 * @param <T>	the type of the object to check
	 * @param v		the object to check for <code>null</code>
	 * @param fun	the function to apply
	 * @return		<code>true</code> if the object is not null.
	 */
	public static <T> boolean applyIfNotNull( T v, UnsafeVoid<Exception> fun ) {
		return applyOnCondition( () -> v != null , fun);
	}
	
	/**
	 * <p>This method return a value, provided by a supplier function, only if a condition is met.</p>
	 * 
	 * @param <R>		the return type
	 * @param condition	the condition to check
	 * @param supplier	the supplier function
	 * @return			the value returned by the supplier if the condition returns <code>true</code> or <code>null</code> otherwise.
	 */
	public static <R> R getOnCondition( UnsafeSupplier<Boolean, Exception> condition, UnsafeSupplier<R, Exception> supplier ) {
		return get( () -> {
			R res = null;
			boolean cond = condition.get().booleanValue();
			if ( cond ) {
				res = supplier.get();
			}	
			return res;
		} );
		
	}
	
	/**
	 *  <p>This method return a value, provided by a supplier function, only if an object is not <code>null</code>.</p>
	 * 
	 * @param <T> 			the type of the object to check for <code>null</code>
	 * @param <R>			the return type
	 * @param v				the object to check for <code>null</code>
	 * @param supplier		the supplier function
	 * @return				the value returned by the supplier if the object is not <code>null</code> or <code>null</code> otherwise.
	 */
	public static <T, R> R getIfNotNull( T v, UnsafeSupplier<R, Exception> supplier ) {
		return getOnCondition( () -> v != null , supplier );
	}
	
}
