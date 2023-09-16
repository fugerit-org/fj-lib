package test.org.fugerit.java.core.xml;

import java.io.IOException;

import org.fugerit.java.core.xml.XMLException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestXMLEx {

	private static final Exception EX_TEST = new IOException( "junit test scenario" );
	
	@Test
	public void testCreate() {
		Assert.assertNotNull( new XMLException() );
		Assert.assertNotNull( new XMLException( "a" ) );
		Assert.assertNotNull( new XMLException( new IOException( "b" ) ) );
		Assert.assertNotNull( new XMLException( "c", new IOException( "d" ) ) );
		log.info( "test CONVERT_FUN 1 {}", XMLException.CONVERT_FUN );
		log.info( "test CONVERT_FUN 2 {}", XMLException.CONVERT_FUN.apply( null ) );
		log.info( "test CONVERT_FUN 3 {}", XMLException.CONVERT_FUN.apply( new IOException( "e" ) ) );
	}

	@Test
	public void testApplyEX() {
		Assert.assertThrows( XMLException.class ,() -> XMLException.apply( () -> { throw EX_TEST; } ) );
	}

	@Test
	public void testGetEX() {
		Assert.assertThrows( XMLException.class ,() -> XMLException.get( () -> { throw EX_TEST; } ) );
	}
	
}

