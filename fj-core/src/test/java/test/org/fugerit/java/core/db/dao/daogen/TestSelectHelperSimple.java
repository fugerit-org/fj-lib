package test.org.fugerit.java.core.db.dao.daogen;

import java.sql.Statement;

import org.fugerit.java.core.db.daogen.BasicDAOHelper;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.db.daogen.SelectHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSelectHelperSimple extends TestAddressSelectHelper {
	
	
	protected void testQueryHelperSimple( String syntax ) {
		try ( CloseableDAOContext context = new CloseableDAOContextSC( this.getConnection() ) ) {
			try ( Statement stm = context.getConnection().createStatement() ) {
				stm.execute( "SET DATABASE SQL SYNTAX "+syntax+" TRUE" );
			}
			// SelectHelper setup
			BasicDAOHelper<String> daoHelper = new BasicDAOHelper<>(context);
			SelectHelper selectHelper = daoHelper.newSelectHelper( "fugerit.address" );
			// add param
			selectHelper.andEqualParam( COL_INFO , "Test address 01" );
			logger.info( "sql -> {}", selectHelper.getQueryContent() );		// method getQueryContent() provides access to current query buffer
			// result
			String res = daoHelper.loadOneHelper( selectHelper, RSE_ADDRESS_COL_INFO );
			logger.info( "res -> {}", res );
			Assertions.assertNotNull( "Result should not be null" , res );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	void testQuerySelectSimplePostgres() {
		testQueryHelperSimple( "PGS"  );
	}
	
	@Test
	void testQuerySelectSimpleOracle() {
		testQueryHelperSimple( "ORA" );
	}
	
}
