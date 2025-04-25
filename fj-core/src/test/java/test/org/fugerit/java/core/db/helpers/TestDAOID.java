package test.org.fugerit.java.core.db.helpers;

import org.fugerit.java.core.db.helpers.DAOID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestDAOID extends BasicTest {

	private static final byte BYTE_VALUE = 42;
	
	private static final short SHORT_VALUE = BYTE_VALUE;
	
	private static final int INT_VALUE = BYTE_VALUE;
	
	private static final int LONG_VALUE = BYTE_VALUE;
	
	private static final String STRING_VALUE = String.valueOf( BYTE_VALUE );
	
	private static final DAOID DAOID_VALUE = DAOID.valueOf( LONG_VALUE );
	
	@Test
	void testValueFromByte() {
		DAOID id = DAOID.valueOf( BYTE_VALUE );
		log.info( "value : {}", id );
		Assertions.assertEquals( DAOID_VALUE , id );
	}
	
	@Test
	void testValueFromShort() {
		DAOID id = DAOID.valueOf( SHORT_VALUE );
		log.info( "value : {}", id );
		Assertions.assertEquals( DAOID_VALUE , id );
	}

	@Test
	void testValueFromInt() {
		DAOID id = DAOID.valueOf( INT_VALUE );
		log.info( "value : {}", id );
		Assertions.assertEquals( DAOID_VALUE , id );
	}
	
	@Test
	void testValueFromLong() {
		DAOID id = DAOID.valueOf( LONG_VALUE );
		log.info( "value : {}", id );
		Assertions.assertEquals( DAOID_VALUE , id );
	}
	
	@Test
	void testValueFromString() {
		DAOID id = DAOID.valueOf( STRING_VALUE );
		log.info( "value : {}", id );
		Assertions.assertEquals( DAOID_VALUE , id );
	}
	
	
	@Test
	void testValueOfNullZeroNull() {
		DAOID id = DAOID.valueOfNullZero( 0 );
		log.info( "value : {}", id );
		Assertions.assertNull( id );
	}
	
	@Test
	void testValueOfNullZero() {
		DAOID id = DAOID.valueOfNullZero( LONG_VALUE );
		log.info( "value : {}", id );
		Assertions.assertEquals( DAOID_VALUE , id );
	}
	
	
	@Test
	void testPrint() {
		log.info( "byteValue : {}", DAOID_VALUE.byteValue() );
		log.info( "shortValue : {}", DAOID_VALUE.shortValue() );
		log.info( "intValue : {}", DAOID_VALUE.intValue() );
		log.info( "longValue : {}", DAOID_VALUE.longValue() );
		log.info( "floatValue : {}", DAOID_VALUE.floatValue() );
		log.info( "doubleValue : {}", DAOID_VALUE.doubleValue() );
		log.info( "toString : {}", DAOID_VALUE.toString() );
		Assertions.assertEquals( STRING_VALUE, DAOID_VALUE.toString() );
	}
	
	@Test
	void testHashCode() {
		Assertions.assertEquals( DAOID_VALUE.hashCode() , DAOID.valueOf( STRING_VALUE ).hashCode() );
	}
	
}
