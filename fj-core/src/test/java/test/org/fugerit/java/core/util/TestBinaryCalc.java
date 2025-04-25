package test.org.fugerit.java.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fugerit.java.core.util.BinaryCalc;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;

class TestBinaryCalc extends BasicTest {
	
	@Test
	void hexToLongFF() {
		long res = BinaryCalc.hexToLong( "FF" );
		logger.info( "TEST hex 1 : {}", res );
		assertEquals( 255L , res );
	}
	
	@Test
	void hexToLongF() {
		long res = BinaryCalc.hexToLong( "F" );
		logger.info( "TEST hex 2 : {}", res );
		assertEquals( 15L , res );
	}
	
	@Test
	void hexToLong7BB() {
		long res = BinaryCalc.hexToLong( "7BB" );
		logger.info( "TEST hex 3 : {}", res );
		assertEquals( 1979L , res );
	}
	
	@Test
	void octToLong255() {
		long res = BinaryCalc.octToLong( "377" );
		logger.info( "TEST oct 1 : {}", res );
		assertEquals( 255L , res );
	}
	
	
	@Test
	void binToLong55() {
		long res = BinaryCalc.binToLong( "11111111" );
		logger.info( "TEST bin 1 : {}", res );
		assertEquals( 255L , res );
	}
	
	@Test
	void longToHex255() {
		String res = BinaryCalc.longToHex( 255 );
		logger.info( "TEST long 1 : {}", res );
		assertEquals( "FF" , res );
	}
	
	@Test
	void longToOct255() {
		String res = BinaryCalc.longToOct( 255 );
		logger.info( "TEST long 2 : {}", res );
		assertEquals( "377" , res );
	}
	
	@Test
	void longToBin255() {
		String res = BinaryCalc.longToBin( 255 );
		logger.info( "TEST long 3 : {}", res );
		assertEquals( "11111111" , res );
	}
	
	@Test
	void longToHex43133() {
		String res = BinaryCalc.longToHex( 43133 );
		logger.info( "TEST long 4 : {}", res );
		assertEquals( "A87D" , res );
	}

}
