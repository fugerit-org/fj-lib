/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.lang.helpers;

public class ConcatHelper {

	public static final String CONCAT_SEPARATOR_DEFAULT = "/";
	
	public static String convertNullToBlank( String v ) {
		String r = "";
		if ( v != null ) {
			r = v;
		}
		return r;
	}
	
	public static String concatWithDefaultSeparator( String... values ) {
		return concat( CONCAT_SEPARATOR_DEFAULT, values );
	}
	
	public static String concat( String separator, String... values ) {
		StringBuilder buffer = new StringBuilder();
		buffer.append( convertNullToBlank( values[0] ) );
		for ( int k=1; k<values.length; k++ ) {
			buffer.append( separator );
			buffer.append( convertNullToBlank( values[k] ) );	
		}
		return buffer.toString();
	}
	
}
