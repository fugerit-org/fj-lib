package org.fugerit.java.core.util;

public class ObjectUtils {

	public static String toDefaultString( Object obj, PropertyEntry ...entries ) {
		StringBuilder b = new StringBuilder();
		if ( obj != null ) {
			b.append( obj.getClass().getSimpleName() );
		}
		PropertyEntry.printAll( b, entries );
		return b.toString();
	}
	
}
