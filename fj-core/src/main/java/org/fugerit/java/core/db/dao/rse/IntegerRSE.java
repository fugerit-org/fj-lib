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
 * Result Set Extractor for Integer
 * 
 * @author Fugerit
 *
 */
public class IntegerRSE extends SingleColumnRSE<Integer> {

	public static final IntegerRSE DEFAULT = new IntegerRSE();
	
	public IntegerRSE() {
		super();
	}

	public IntegerRSE(int index) {
		super(index);
	}

	public IntegerRSE(String name) {
		super(name);
	}

	@Override
	protected Integer convert(Object o) {
		Integer c = null;
		if ( o instanceof Number ) {
			c = ((Number)o).intValue();
		}
		return c;
	}
	
}
