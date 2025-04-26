package test.org.fugerit.java.core.lang.helpers.filter;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.fugerit.java.core.lang.helpers.filter.FilterApplyDefault;
import org.fugerit.java.core.lang.helpers.filter.FilterFacade;
import org.fugerit.java.core.lang.helpers.filter.FilterInfo;
import org.fugerit.java.core.lang.helpers.filter.FilterInfoDefault;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;
import test.org.fugerit.java.core.lang.helpers.reflect.TestModelOne;
import test.org.fugerit.java.core.lang.helpers.reflect.TestModelTwo;

class TestPathHelper extends BasicTest {
	
	private static final TestModelOne TEST_CASE_001 = new TestModelOne( new BigDecimal( 1 ), "value 001_1", new TestModelTwo( new BigDecimal( 2 ), "value 001_2" )  );
	
	private static final TestModelOne TEST_CASE_002 = new TestModelOne();
	
	private static final TestModelOne TEST_CASE_003 = new TestModelOne( new BigDecimal( 3 ), "value 003_1", null  );
	
	private void testFilterWorker( Object target, Collection<FilterInfo> filters, boolean expectedResult ) {
		try {
			boolean result = FilterFacade.accept(target, filters);
			if ( result == expectedResult ) {
				logger.info( "Risultato OK");	
			} else {
				fail( "Expected "+expectedResult+" found : "+result );
			}
		} catch (Exception e) {
			String message = e.getMessage();
			logger.info( message, e );
			fail( message );
		}
	}
	
	@Test
	void test001_A() {
		Collection<FilterInfo> filters = new ArrayList<FilterInfo>();
		filters.add( FilterInfoDefault.newFilter( TEST_CASE_001.getKid().getValueTwo(), FilterApplyDefault.EV_EQUALS, "kid.valueTwo" ) );
		this.testFilterWorker( TEST_CASE_001 , filters, true );
	}
	
	@Test
	void test001_B() {
		Collection<FilterInfo> filters = new ArrayList<FilterInfo>();
		filters.add( FilterInfoDefault.newFilter( TEST_CASE_001.getKid().getValueTwo(), null, "kid.valueTwo" ) );
		this.testFilterWorker( TEST_CASE_001 , filters, true );
	}
	
	@Test
	void test001_C() {
		Collection<FilterInfo> filters = new ArrayList<FilterInfo>();
		filters.add( FilterInfoDefault.newFilter( TEST_CASE_001.getKid().getValueTwo(), FilterApplyDefault.EV_NOT_EQUALS, "kid.valueTwo" ) );
		this.testFilterWorker( TEST_CASE_001 , filters, false );
	}

	@Test
	void test001_D() {
		Collection<FilterInfo> filters = new ArrayList<FilterInfo>();
		filters.add( FilterInfoDefault.newFilter( "test", FilterApplyDefault.EV_EQUALS, "kid.valueTwo" ) );
		this.testFilterWorker( TEST_CASE_001 , filters, false );
	}
	
	@Test
	void test002_A() {
		Collection<FilterInfo> filters = new ArrayList<FilterInfo>();
		filters.add( FilterInfoDefault.newFilter( FilterApplyDefault.EV_IS_NULL, "kid.valueTwo" ) );
		this.testFilterWorker( TEST_CASE_002 , filters, true );
	}

	@Test
	void test003_A() {
		Collection<FilterInfo> filters = new ArrayList<FilterInfo>();
		filters.add( FilterInfoDefault.newFilter( FilterApplyDefault.EV_IS_NULL, "kid" ) );
		this.testFilterWorker( TEST_CASE_003 , filters, true );
	}
	
}
