package test.org.fugerit.java.core.cfg;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.junit.Assert;
import org.junit.Test;

public class TestConfigRuntimeException {
	
	private static final IOException TEST_IO_EX = new IOException( "test io ex" );
	
	@Test
	public void testEx1() {
		Assert.assertNotNull( new ConfigRuntimeException() );
	}
	
	@Test
	public void testEx2() {
		Assert.assertNotNull( new ConfigRuntimeException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assert.assertNotNull( new ConfigRuntimeException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assert.assertNotNull( new ConfigRuntimeException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	public void testEx5() {
		Assert.assertNotNull( ConfigRuntimeException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assert.assertNotNull( ConfigRuntimeException.convertEx( "g" , new ConfigRuntimeException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assert.assertNotNull( ConfigRuntimeException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx8() {
		Assert.assertNotNull( new ConfigRuntimeException( 1 ) );
	}
	
	@Test
	public void testEx9() {
		Assert.assertNotNull( new ConfigRuntimeException( "h", 2 ) );
	}
	
	@Test
	public void testEx10() {
		Assert.assertNotNull( new ConfigRuntimeException( "i", new ConfigRuntimeException( "l" ), 3 ) );
	}
	
	@Test
	public void testEx11() {
		Assert.assertNotNull( new ConfigRuntimeException( new ConfigRuntimeException( "m" ), 4 ) );
	}
	
	@Test
	public void testEx12() throws ConfigRuntimeException {
		Assert.assertThrows( ConfigRuntimeException.class, () -> ConfigRuntimeException.stadardExceptionWrapping( TEST_IO_EX ) );
	}
	
}
