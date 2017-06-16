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
 * Result Set Extractor for Double
 * 
 * @author Fugerit
 *
 */
public class DoubleRSE extends SingleColumnRSE<Double> {

	public static final DoubleRSE DEFAULT = new DoubleRSE();
	
	public DoubleRSE() {
		super();
	}

	public DoubleRSE(int index) {
		super(index);
	}

	public DoubleRSE(String name) {
		super(name);
	}

	@Override
	protected Double convert(Object o) {
		Double c = null;
		if ( o instanceof Number ) {
			c = ((Number)o).doubleValue();
		}
		return c;
	}

}
