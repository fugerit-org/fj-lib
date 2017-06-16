/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.dao.rse;

/**
 * Result Set Extractor for Long
 * 
 * @author Fugerit
 *
 */
public class LongRSE extends SingleColumnRSE<Long> {

	public static final LongRSE DEFAULT = new LongRSE();
	
	public LongRSE() {
		super();
	}

	public LongRSE(int index) {
		super(index);
	}

	public LongRSE(String name) {
		super(name);
	}

	@Override
	protected Long convert(Object o) {
		Long c = null;
		if ( o instanceof Number ) {
			c = ((Number)o).longValue();
		}
		return c;
	}
	
}
