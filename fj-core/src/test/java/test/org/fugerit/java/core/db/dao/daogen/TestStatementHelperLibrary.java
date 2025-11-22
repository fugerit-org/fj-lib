package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.daogen.*;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.org.fugerit.java.core.db.TestBasicDBHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
class TestStatementHelperLibrary extends TestBasicDBHelper {

	private PreparedStatement worker(DAOContext context, Integer queryTimeoutGeneral, Integer queryTimeoutCustom, Integer fetchSizeGeneral, Integer fetchSizeCustom) throws DAOException, SQLException {
		PreparedStatement ps = context.getConnection().prepareStatement( "SELECT * FROM DUAL" );
		return StatementHelperLibrary.newHelperPipeline(
			StatementHelperLibrary.DO_NOTHING_STATEMENT_HELPER,
				StatementHelperLibrary.newHelperWithQueryTimeout( queryTimeoutGeneral ),
				StatementHelperLibrary.newHelperWithFetchSize( fetchSizeGeneral )
		).apply( ps, StatementHelperLibrary.withQueryTimeout(
				StatementHelperLibrary.withFetchSize( context, fetchSizeCustom ), queryTimeoutCustom ) );
	}

	@Test
	void testStatementHelperLibrary() throws Exception {
		try ( CloseableDAOContextSC context =  new CloseableDAOContextSC( newConnection() ) ) {
			log.info( "test with no query timeout or fetch size" );
			PreparedStatement ps1 = this.worker( context, null, null, null, null );
			Assertions.assertEquals( 0, ps1.getQueryTimeout() );
			Assertions.assertEquals( 0, ps1.getFetchSize() );
			log.info( "test with general query timeout and fetch size" );
			Integer queryTimeoutGeneral = 10;
			Integer fetchSizeGeneral = 20;
			PreparedStatement ps2 = this.worker( context, queryTimeoutGeneral, null, fetchSizeGeneral, null );
			Assertions.assertEquals( queryTimeoutGeneral, ps2.getQueryTimeout() );
			Assertions.assertEquals( fetchSizeGeneral, ps2.getFetchSize() );
			log.info( "test with general + custom query timeout and fetch size" );
			Integer queryTimeoutCustom = 30;
			Integer fetchSizeCustom = 40;
			PreparedStatement ps3 = this.worker( context, queryTimeoutGeneral, queryTimeoutCustom, fetchSizeGeneral, fetchSizeCustom );
			Assertions.assertEquals( queryTimeoutCustom, ps3.getQueryTimeout() );
			Assertions.assertEquals( fetchSizeCustom, ps3.getFetchSize() );
			log.info( "log do nothing" );
			PreparedStatement psDoNothing = StatementHelperLibrary.DO_NOTHING_STATEMENT_HELPER.apply( context.getConnection().prepareStatement( "SELECT * FROM DUAL" ), context );
			Assertions.assertEquals( 0, psDoNothing.getQueryTimeout() );
			Assertions.assertEquals( 0, psDoNothing.getFetchSize() );
			log.info( "log default statement helper" );
			PreparedStatement psDefault = StatementHelperLibrary.DEFAULT_STATEMENT_HELPER.apply( context.getConnection().prepareStatement( "SELECT * FROM DUAL" ),
					StatementHelperLibrary.withFetchSize( StatementHelperLibrary.withQueryTimeout( context, queryTimeoutCustom ), fetchSizeCustom ) );
			Assertions.assertEquals( queryTimeoutCustom, psDefault.getQueryTimeout() );
			Assertions.assertEquals( fetchSizeCustom, psDefault.getFetchSize() );
		}
	}
	
}
