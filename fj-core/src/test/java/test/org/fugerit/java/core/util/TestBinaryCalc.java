package test.org.fugerit.java.core.util;

import org.fugerit.java.core.util.BinaryCalc;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestBinaryCalc extends BasicTest {
	
	@Test
	public void hexToLong() {
		logger.info( "TEST : {}", BinaryCalc.hexToLong( "FF" ) );
	}

}
