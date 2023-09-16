package test.org.fugerit.java.core.db.dao.rse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.fugerit.java.core.db.connect.ConnectionFacade;
import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.db.dao.rse.BigDecimalRSE;
import org.fugerit.java.core.db.dao.rse.DoubleRSE;
import org.fugerit.java.core.db.dao.rse.IntegerRSE;
import org.fugerit.java.core.db.dao.rse.LongRSE;
import org.fugerit.java.core.db.dao.rse.OptionItemRSE;
import org.fugerit.java.core.db.dao.rse.PropertyRSE;
import org.fugerit.java.core.db.dao.rse.SingleColumnRSE;
import org.fugerit.java.core.db.dao.rse.StringRSE;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.core.util.collection.OptionItem;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.db.TestBasicDBHelper;

@Slf4j
public class TestRSE extends TestBasicDBHelper {

	private static final String TEST_USERNAME = "user1";
	
	private static final int TEST_STATE = 1;
	
	private static Class<?>[] PARAM_STRING = { String.class };
	
	private static Class<?>[] PARAM_INTEGER = { Integer.TYPE };
	
	private <T> T worker( String sql, String userId, RSExtractor<T> rse, String colName ) throws SQLException {
		T res = null;
		try ( Connection conn = newConnection() ) {
			res = DAOUtilsNG.extraOne( conn , sql, rse, userId );
			if ( colName != null ) {
				Class<?> c = rse.getClass();
				SafeFunction.apply( () -> {
					@SuppressWarnings({ "unchecked" })
					RSExtractor<T> altResName = (RSExtractor<T>) c.getConstructor( PARAM_STRING ).newInstance( colName );
					log.info( "result col name {} -> {}", colName, DAOUtilsNG.extraOne( conn , sql, altResName, userId ) ); 
					@SuppressWarnings({ "unchecked" })
					RSExtractor<T> altResIndex = (RSExtractor<T>) c.getConstructor( PARAM_INTEGER ).newInstance( 1 );
					log.info( "result col index {} -> {}", colName, DAOUtilsNG.extraOne( conn , sql, altResIndex, userId ) ); 
				} );
				;
			}
		}
		return res;
	}
	
	@Test
	public void testRSEString() throws SQLException {
		String result = this.worker( "SELECT username FROM fugerit.user WHERE username = ?" , TEST_USERNAME, StringRSE.DEFAULT, "username" );
		Assert.assertEquals( "user1" , result );
	}
	
	@Test
	public void testRSEBigDecimal() throws SQLException {
		BigDecimal result = this.worker( "SELECT state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, BigDecimalRSE.DEFAULT, "state" );
		Assert.assertEquals( BigDecimal.valueOf( TEST_STATE ) , result );
		// test convert method
		BigDecimalRSE tester = new BigDecimalRSE() {
			public String toString() {
				return String.valueOf( "TestRSE:"+this.convert( null ) );
			}
		};
		log.info( "test : {}", tester.toString() );
	}

	@Test
	public void testRSEDouble() throws SQLException {
		Double result = this.worker( "SELECT state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, DoubleRSE.DEFAULT, "state" );
		Assert.assertEquals( Double.valueOf( TEST_STATE ) , result );
	}
	
	@Test
	public void testRSEInteger() throws SQLException {
		Integer result = this.worker( "SELECT state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, IntegerRSE.DEFAULT, "state" );
		Assert.assertEquals( Integer.valueOf( TEST_STATE ) , result );
	}
	
	@Test
	public void testRSELong() throws SQLException {
		Long result = this.worker( "SELECT state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, LongRSE.DEFAULT, "state" );
		Assert.assertEquals( Long.valueOf( TEST_STATE ) , result );
	}
	
	@Test
	public void testRSEFail() throws SQLException {
		SingleColumnRSE<Long> rse = new SingleColumnRSE<Long>() {	
			@Override
			protected Long convert(Object o) {
				return null;
			}
		};
		Assert.assertThrows( DAORuntimeException.class , () -> this.worker( "SELECT state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, rse, "state" ) );
	}
	
	private <T> void worker( SingleColumnRSE<T> rse ) {
		log.info( "use column : {}, column name : {}", rse.isUseColumnName(), rse.getColumnName() );
		log.info( "use index : {}, column index : {}", rse.isUseColumnIndex(), rse.getColumnIndex() );
	}
	
	@Test
	public void testSingleColumn() {
		boolean ok = false;
		this.worker( new StringRSE( 1 ) );
		this.worker( new StringRSE( "test" ) );
		ok = true;
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testOptionItemRSE() throws SQLException {
		OptionItem result1 = this.worker( "SELECT username FROM fugerit.user WHERE username = ?" , TEST_USERNAME, OptionItemRSE.getInstance( "username" ), null );
		Assert.assertEquals( TEST_USERNAME , result1.getValue() );
		OptionItemRSE rse = OptionItemRSE.getInstance( "state", "username" );
		OptionItem result2 = this.worker( "SELECT username, state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, rse, null );
		Assert.assertEquals( String.valueOf( TEST_STATE ) , result2.getValue() );
		Assert.assertEquals( TEST_USERNAME , result2.getLabel() );
		log.info( "result2 label field : {}, value field : {}", rse.getLabelField(), rse.getValueField() );
	}
	
	@Test
	public void testPropertyReusableRSE() throws SQLException {
		PropertyRSE rse = PropertyRSE.newReusableRSE();
		Properties result = this.worker( "SELECT username, state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, rse, null );
		log.info( "result : {}", result );
		Assert.assertEquals( TEST_USERNAME , result.getProperty( "USERNAME" ) );
	}
	
	@Test
	public void testPropertyCachingRSE() throws SQLException {
		PropertyRSE rse = PropertyRSE.newAutoCachingMetadataRSE();
		Properties result = this.worker( "SELECT username, state FROM fugerit.user WHERE username = ?" , TEST_USERNAME, rse, null );
		Assert.assertEquals( TEST_USERNAME , result.getProperty( "USERNAME" ) );
	}
	
	private static final String SIMPLE_QUERY = "SELECT * FROM fugerit.user";
	
	@Test
	public void tesConnectionFacade() throws Exception {
		try ( ConnectionFactoryCloseable cf = ConnectionFactoryImpl.wrap( 
				ConnectionFactoryImpl.newInstance( PropsIO.loadFromClassLoaderSafe( TestBasicDBHelper.DEFAULT_DB_CONN_PATH ) ) ) ) {
			try ( Connection conn = cf.getConnection();  
					Statement stm = conn.createStatement(); 
					ResultSet rs = stm.executeQuery( SIMPLE_QUERY ) ) {
				ConnectionFacade.closeLoose( rs );
				ConnectionFacade.closeLoose( stm );
				ConnectionFacade.closeLoose( conn );
			}
			try ( Connection conn = cf.getConnection();  
					Statement stm = conn.createStatement(); 
					ResultSet rs = stm.executeQuery( SIMPLE_QUERY ) ) {
				Assert.assertTrue( ConnectionFacade.closeLoose(conn, stm, rs) );
			}
			try ( Connection conn = cf.getConnection();  
					Statement stm = conn.createStatement() ) {
				Assert.assertFalse( ConnectionFacade.closeLoose(conn, stm, null) );
			}
			try ( Connection conn = cf.getConnection() ) {
				Assert.assertFalse( ConnectionFacade.closeLoose(conn, null, null) );
			}
			Assert.assertFalse( ConnectionFacade.closeLoose(null, null, null) );
			try ( Connection conn = cf.getConnection();  
					Statement stm = conn.createStatement() ) {
				Assert.assertTrue( ConnectionFacade.closeLoose(conn, stm) );
			}
			Assert.assertFalse( ConnectionFacade.closeLoose(null, null) );
			try ( Connection conn = cf.getConnection() ) {
				Assert.assertFalse( ConnectionFacade.closeLoose(conn, null) );
			}
		}
	}

}
