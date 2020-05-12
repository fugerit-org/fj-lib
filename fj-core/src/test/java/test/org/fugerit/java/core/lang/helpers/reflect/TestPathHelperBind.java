package test.org.fugerit.java.core.lang.helpers.reflect;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.fugerit.java.core.lang.helpers.reflect.PathHelper;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestPathHelperBind extends BasicTest {
	
	@Test
	public void bind001() {
		try {
			long testIdTwo = 2;
			long testIdTwoBind = 3;
			TestModelOne target = new TestModelOne();
			target.setIdOne( new BigDecimal( 1 ) );
			TestModelTwo value = new TestModelTwo();
			value.setIdTwo( new BigDecimal ( testIdTwo ) );
			boolean res = PathHelper.bind( "kid" , target, value );
			if ( target.getKid().getIdTwo().longValue() == testIdTwo ) {
				res = PathHelper.bind( "kid.idTwo" , target, new BigDecimal( testIdTwoBind ) );
				if ( target.getKid().getIdTwo().longValue() == testIdTwoBind ) {
					logger.info( "Result OK! bind {} -> {}", res, target.getKid().getIdTwo() );	
				} else {
					fail( "Second binding failed" );
				}
			} else {
				fail( "First binding failed" );
			}
		} catch (Exception e) {
			String message = e.getMessage();
			logger.info( message, e );
			fail( message );
		}
	}
	
}
