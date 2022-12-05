package org.fugerit.java.core.util;

public class ObjectUtils {

	public static <T> T objectWithDefault( T value, T def ) {
		T res = def;
		if ( value != null ) {
			res = value;
		}
		return res;
	}
	
	public static String toDefaultString( Object obj, PropertyEntry ...entries ) {
		StringBuilder b = new StringBuilder();
		if ( obj != null ) {
			b.append( obj.getClass().getSimpleName() );
		}
		PropertyEntry.printAll( b, entries );
		return b.toString();
	}
	
}
