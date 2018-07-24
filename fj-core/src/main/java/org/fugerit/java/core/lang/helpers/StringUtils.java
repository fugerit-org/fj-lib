package org.fugerit.java.core.lang.helpers;

import java.util.HashSet;
import java.util.Set;

public class StringUtils {

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
	
}
