package test.org.fugerit.java.core.xml;

import java.io.IOException;

import org.fugerit.java.core.xml.XMLException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestXMLEx {

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
	
}

