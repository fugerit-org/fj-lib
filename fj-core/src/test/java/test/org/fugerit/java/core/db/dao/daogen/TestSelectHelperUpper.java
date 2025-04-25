package test.org.fugerit.java.core.db.dao.daogen;

import java.sql.Statement;

import org.fugerit.java.core.db.daogen.BasicDAOHelper;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.db.daogen.SelectHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSelectHelperUpper extends TestAddressSelectHelper {
	
	protected void testQueryHelperIgnoreCase( String syntax ) {
		try ( CloseableDAOContext context = new CloseableDAOContextSC( this.getConnection() ) ) {
			try ( Statement stm = context.getConnection().createStatement() ) {
				stm.execute( "SET DATABASE SQL SYNTAX "+syntax+" TRUE" );
			}
			// SelectHelper setup
			BasicDAOHelper<String> daoHelper = new BasicDAOHelper<>(context);
			SelectHelper selectHelper = daoHelper.newSelectHelper( "fugerit.address" );
			// select helper is basically a query builder, it is possible to add functions, instead of the simple column name
			String column = String.format( "UPPER(%s)" , COL_INFO );		// will add 'UPPER(INFO)'
			// it is possible to transform upper case the value to be compared
			String value = "test address 01".toUpperCase();
			// this will perform an ignore case comparison, as both the column and the value are now UPPERCASE
			selectHelper.andEqualParam( column , value );
			logger.info( "sql -> {}", selectHelper.getQueryContent() );		// method getQueryContent() provides access to current query buffer
			// the result
			String res = daoHelper.loadOneHelper( selectHelper, RSE_ADDRESS_COL_INFO );
			logger.info( "res -> {}", res );
			Assertions.assertNotNull( "Result should not be null" , res );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	void testQuerySelectIgnoreCasePostgres() {
		testQueryHelperIgnoreCase( "PGS"  );
	}
	
	@Test
	void testQuerySelectgnoreCaseOracle() {
		testQueryHelperIgnoreCase( "ORA" );
	}
	
}
