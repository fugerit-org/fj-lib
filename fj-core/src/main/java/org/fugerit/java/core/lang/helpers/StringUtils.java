package org.fugerit.java.core.lang.helpers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StringUtils {

	private StringUtils() {}
	
	public static final String[] EMPTY_ARRAY = new String[0];
	
	public static boolean isEmpty( String s ) {
		return s == null || s.trim().length() == 0;
	}
	
	public static boolean isNotEmpty( String s ) {
		return !isEmpty( s );
	}

	public static String valueWithDefault( String s, String def ) {
		String r = def;
		if ( StringUtils.isNotEmpty( s ) ) {
			r = s;
		}
		return r;
	}
	
	public static Set<String> newSet( String... values ) {
		HashSet<String> set = new HashSet<String>();
		for (int k=0; k<values.length; k++ ) {
			set.add( values[k] );
		}
		return set;
	}
	
	public static String concat( String separator, Collection<String> c  ) {
		return concat( separator, c.toArray( EMPTY_ARRAY ) ); 
	}
	
	public static String concat( String separator, String... value ) {
		StringBuilder builder = new StringBuilder();
		for ( int k=0; k<value.length; k++ ) {
			if ( k!=0 ) {
				builder.append( separator );
			}
			builder.append( value[k] );
		}
		return builder.toString();
	}
	
	public static boolean appendIfNotNull( StringBuilder builder, Object value ) {
		boolean append = (value != null);
		if ( append ) {
			builder.append( value );
		}
		return append;
	}
	
}
