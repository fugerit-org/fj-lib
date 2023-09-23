package org.fugerit.java.core.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.fugerit.java.core.function.Action;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.UnsafeSupplier;
import org.fugerit.java.core.function.UnsafeVoid;

public class IteratorHelper {

	private IteratorHelper() {}

	public static <T> Iterator<T> createSimpleIteratorSafe( UnsafeSupplier<Boolean, Exception> checkNext, UnsafeSupplier<T, Exception> getNext ) {
		return createSimpleIteratorSafe(checkNext, getNext, null );
	}
	
	public static <T> Iterator<T> createSimpleIteratorSafe( UnsafeSupplier<Boolean, Exception> checkNext, UnsafeSupplier<T, Exception> getNext, UnsafeVoid<Exception> removeFun ) {
		return createSimpleIterator( 
					() -> SafeFunction.get( checkNext::get ).booleanValue(), 
					() -> SafeFunction.get( getNext::get ),
					(removeFun != null) ? () -> SafeFunction.apply( removeFun::apply ) : null );
	}
	
	public static <T> Iterator<T> createSimpleIterator( BooleanSupplier checkNext, Supplier<T> getNext ) {
		return createSimpleIterator(checkNext, getNext, null );
	}
	
	public static <T> Iterator<T> createSimpleIterator( BooleanSupplier checkNext, Supplier<T> getNext, Action removeFun ) {
		return new Iterator<T>() {
			boolean hasNext = false;
			@Override
			public boolean hasNext() {
				this.hasNext = checkNext.getAsBoolean();
				return hasNext;
			}
			@Override
			public T next() {
				if ( this.hasNext ) {
					return getNext.get();
				} else {
					throw new NoSuchElementException( "Call next() only if hasNext() returns true" );
				}
			}
			@Override
			public void remove() {
				if ( removeFun == null ) {
					throw new UnsupportedOperationException( "remove" );
				} else {
					removeFun.apply();
				}
			}
		};
	}
	
}
