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
package org.fugerit.java.core.db.dao.rse;

/**
 * Result Set Extractor for String
 * 
 * @author Fugerit
 *
 */
public class StringRSE extends SingleColumnRSE<String> {

	public static final StringRSE DEFAULT = new StringRSE();
	
	public StringRSE() {
		super();
	}
	
	public StringRSE(int index) {
		super(index);
	}

	public StringRSE(String name) {
		super(name);
	}
	
	@Override
	protected String convert(Object o) {
		String c = null;
		if ( o instanceof String ) {
			c = (String)o;
		}
		return c;
	}

}
