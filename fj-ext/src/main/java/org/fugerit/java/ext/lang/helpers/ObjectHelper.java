package org.fugerit.java.ext.lang.helpers;
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


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ObjectHelper {

	public static String objectToStringDefault( Object value ) {
		return ToStringBuilder.reflectionToString( value, ToStringStyle.SHORT_PREFIX_STYLE );
	}
	
}
