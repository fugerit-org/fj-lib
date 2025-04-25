package test.org.fugerit.java.core.xml;

import java.io.IOException;

import org.fugerit.java.core.xml.XMLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestXMLEx {

	private static final Exception EX_TEST = new IOException( "junit test scenario" );
	
	@Test
	void testCreate() {
		Assertions.assertNotNull( new XMLException() );
		Assertions.assertNotNull( new XMLException( "a" ) );
		Assertions.assertNotNull( new XMLException( new IOException( "b" ) ) );
		Assertions.assertNotNull( new XMLException( "c", new IOException( "d" ) ) );
		log.info( "test CONVERT_FUN 1 {}", XMLException.CONVERT_FUN );
		log.info( "test CONVERT_FUN 2 {}", XMLException.CONVERT_FUN.apply( null ) );
		log.info( "test CONVERT_FUN 3 {}", XMLException.CONVERT_FUN.apply( new IOException( "e" ) ) );
	}

	@Test
	void testApplyEX() {
		Assertions.assertThrows( XMLException.class ,() -> XMLException.apply( () -> { throw EX_TEST; } ) );
	}

	@Test
	void testGetEX() {
		Assertions.assertThrows( XMLException.class ,() -> XMLException.get( () -> { throw EX_TEST; } ) );
	}
	
}

