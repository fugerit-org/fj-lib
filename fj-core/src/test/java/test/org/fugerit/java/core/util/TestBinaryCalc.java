package test.org.fugerit.java.core.util;

import static org.junit.Assert.assertEquals;

import org.fugerit.java.core.util.BinaryCalc;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestBinaryCalc extends BasicTest {
	
	@Test
	public void hexToLong() {
		long res = BinaryCalc.hexToLong( "FF" );
		logger.info( "TEST : {}", res );
		assertEquals( 255L , res );
	}

}
