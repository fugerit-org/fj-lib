package test.org.fugerit.java.core.cfg;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.cfg.ConfigException;
import org.junit.Assert;
import org.junit.Test;

public class TestConfigException {
	
	@Test
	public void testEx1() {
		Assert.assertNotNull( new ConfigException() );
	}
	
	@Test
	public void testEx2() {
		Assert.assertNotNull( new ConfigException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assert.assertNotNull( new ConfigException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assert.assertNotNull( new ConfigException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	public void testEx5() {
		Assert.assertNotNull( ConfigException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assert.assertNotNull( ConfigException.convertEx( "g" , new ConfigException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assert.assertNotNull( ConfigException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx8() {
		Assert.assertNotNull( new ConfigException( 1 ) );
	}
	
	@Test
	public void testEx9() {
		Assert.assertNotNull( new ConfigException( "h", 2 ) );
	}
	
	@Test
	public void testEx10() {
		Assert.assertNotNull( new ConfigException( "i", new ConfigException( "l" ), 3 ) );
	}
	
	@Test
	public void testEx11() {
		Assert.assertNotNull( new ConfigException( new ConfigException( "m" ), 4 ) );
	}
	
	@Test
	public void testEx12() throws ConfigException {
		Assert.assertThrows( ConfigException.class, () -> ConfigException.stadardExceptionWrapping( new IOException( "n" ) ) );
	}
	
}
