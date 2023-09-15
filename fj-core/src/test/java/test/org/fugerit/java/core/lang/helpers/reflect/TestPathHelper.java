package test.org.fugerit.java.core.lang.helpers.reflect;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.fugerit.java.core.lang.helpers.reflect.PathHelper;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestPathHelper extends BasicTest {
	
	private static final int EXPECTED_RESULT_EXCEPTION = 1;
	private static final int EXPECTED_RESULT_NULL = 2;
	private static final int EXPECTED_RESULT_VALUE = 3;
	
	private static final TestModelOne TEST_CASE_001 = new TestModelOne( new BigDecimal( 1 ), "value 001_1", new TestModelTwo( new BigDecimal( 2 ), "value 001_2" )  );
	
	private static final TestModelOne TEST_CASE_002 = new TestModelOne();
	
	private static final TestModelOne TEST_CASE_003 = new TestModelOne( new BigDecimal( 3 ), "value 003_1", null  );
	
	private void testLookupWorker( Object target, String path, boolean useMethodLookup, boolean continueOnNull, int expectedResult ) {
		try {
			Object result = null;
			try {
				if ( useMethodLookup ) {
					result = PathHelper.lookupMethod(path, target, continueOnNull);
				} else {
					result = PathHelper.lookup(path, target, continueOnNull);
				}
				if ( result == null && expectedResult != EXPECTED_RESULT_NULL ) {
					throw new Exception( "Expected not null, found null" );
				} else if ( result != null && expectedResult == EXPECTED_RESULT_NULL ) {
					throw new Exception( "Expected null, found "+result );
				}
			} catch (Exception e) {
				if ( expectedResult == EXPECTED_RESULT_EXCEPTION ) {
					logger.info( "Eccezione prevista : "+e, e );
				} else {
					throw e;
				}
			}
			logger.info( "Risultato OK result : {} -> path : {} -> continueOnNull : {} -> expectedResult -> {}", path, continueOnNull, expectedResult );
		} catch (Exception e) {
			String message = e.getMessage();
			logger.info( message, e );
			fail( message );
		}
	}
	
	@Test
	public void test001_A() {
		testLookupWorker( TEST_CASE_001 , "kid.idTwo", false, PathHelper.CONTINUE_ON_NULL, EXPECTED_RESULT_VALUE );
	}
	
	@Test
	public void test001_B() {
		testLookupWorker( TEST_CASE_001 , "getKid.getIdTwo", true, PathHelper.CONTINUE_ON_NULL, EXPECTED_RESULT_VALUE );
	}
	
	@Test
	public void test002_A() {
		testLookupWorker( TEST_CASE_002 , "kid.idTwo", false, PathHelper.CONTINUE_ON_NULL, EXPECTED_RESULT_EXCEPTION );
	}
	
	@Test
	public void test002_B() {
		testLookupWorker( TEST_CASE_002 , "kid", false, PathHelper.CONTINUE_ON_NULL, EXPECTED_RESULT_NULL );
	}
	
	@Test
	public void test002_C() {
		testLookupWorker( TEST_CASE_002 , "kid.idTwo", false, PathHelper.EXIT_ON_NULL, EXPECTED_RESULT_NULL );
	}
	
	@Test
	public void test002_D() {
		testLookupWorker( TEST_CASE_002 , "kidTest", false, PathHelper.EXIT_ON_NULL, EXPECTED_RESULT_EXCEPTION );
	}
	
	@Test
	public void test003_A() {
		testLookupWorker( TEST_CASE_003 , "kid.idTwo", false, PathHelper.CONTINUE_ON_NULL, EXPECTED_RESULT_EXCEPTION );
	}
	
	@Test
	public void test003_B() {
		testLookupWorker( TEST_CASE_003 , "kid.idTwo", false, PathHelper.EXIT_ON_NULL, EXPECTED_RESULT_NULL );
	}
	
}
