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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Result Set Extractor for Long
 * 
 * @author Fugerit
 *
 */
public class BigDecimalRSE extends SingleColumnRSE<BigDecimal> {

	public static final BigDecimalRSE DEFAULT = new BigDecimalRSE();
	
	public BigDecimalRSE() {
		super();
	}

	public BigDecimalRSE(int index) {
		super(index);
	}

	public BigDecimalRSE(String name) {
		super(name);
	}

	@Override
	protected BigDecimal convert(Object o) {
		return null;
	}

	@Override
	public BigDecimal extractNext(ResultSet rs) throws SQLException {
		BigDecimal result = null;
		if ( this.isUseColumnIndex() ) {
			result = rs.getBigDecimal( this.getColumnIndex() );
		} else {
			result = rs.getBigDecimal( this.getColumnName() );
		}
		return result;
	}


	
}
