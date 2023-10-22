package test.org.fugerit.java.core.cfg;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.cfg.ConfigException;
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.core.HelperCheck;

public class TestConfigException {
	
	
	@Test
	public void testApplySilent() throws ConfigException {
		boolean ok = true;
		ConfigException.applySilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testGetSilent() throws ConfigException {
		Object result = ConfigException.getSilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assert.assertNull( result );
	}
	
	@Test
	public void testApplyEX() {
		Assert.assertThrows( ConfigException.class ,() -> ConfigException.apply( () -> { throw new IOException( "junit test scenario" ); } ) );
	}

	@Test
	public void testGetEX() {
		Assert.assertThrows( ConfigException.class ,() -> ConfigException.get( () -> { throw new IOException( "junit test scenario" ); } ) );
	}
	
	@Test
	public void testApply() throws ConfigException {
		HelperCheck check = new HelperCheck();
		Assert.assertNotEquals( HelperCheck.TEST_S1 , check.getField1() );
		ConfigException.apply( () -> check.setField1( HelperCheck.TEST_S1 ) );
		Assert.assertEquals( HelperCheck.TEST_S1 , check.getField1() );
	}

	@Test
	public void testGet() throws ConfigException {
		Assert.assertEquals( "test", ConfigException.get( () -> "test" ) );
	}
	
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
