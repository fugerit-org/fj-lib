package test.org.fugerit.java.core.cfg;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.cfg.ConfigException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.HelperCheck;

@Slf4j
class TestConfigException {
	
	
	@Test
	void testApplySilent() throws ConfigException {
		boolean ok = true;
		ConfigException.applySilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testGetSilent() throws ConfigException {
		Object result = ConfigException.getSilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertNull( result );
	}
	
	@Test
	void testApplyEX() {
		Assertions.assertThrows( ConfigException.class ,() -> ConfigException.apply( () -> { throw new IOException( "junit test scenario" ); } ) );
	}

	@Test
	void testGetEX() {
		Assertions.assertThrows( ConfigException.class ,() -> ConfigException.get( () -> { throw new IOException( "junit test scenario" ); } ) );
	}
	
	@Test
	void testApplyEXMessage() {
		Assertions.assertThrows( ConfigException.class ,() -> ConfigException.applyWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}

	@Test
	void testGetEXMessage() {
		Assertions.assertThrows( ConfigException.class ,() -> ConfigException.getWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}
	
	@Test
	void testApplyEXMessageOk() throws ConfigException {
		boolean ok = true;
		ConfigException.applyWithMessage( () -> log.info( "test ok" ) , "test message" );
		Assertions.assertTrue( ok );
	}

	@Test
	void testGetEXMessageOk() throws ConfigException {
		Assertions.assertNotNull( ConfigException.getWithMessage( () -> "test ok" , "test message" ) );
	}
	
	@Test
	void testApply() throws ConfigException {
		HelperCheck check = new HelperCheck();
		Assertions.assertNotEquals( HelperCheck.TEST_S1 , check.getField1() );
		ConfigException.apply( () -> check.setField1( HelperCheck.TEST_S1 ) );
		Assertions.assertEquals( HelperCheck.TEST_S1 , check.getField1() );
	}

	@Test
	void testGet() throws ConfigException {
		Assertions.assertEquals( "test", ConfigException.get( () -> "test" ) );
	}
	
	@Test
	void testEx1() {
		Assertions.assertNotNull( new ConfigException() );
	}
	
	@Test
	void testEx2() {
		Assertions.assertNotNull( new ConfigException( "a" ) );
	}
	
	@Test
	void testEx3() {
		Assertions.assertNotNull( new ConfigException( new SQLException( "b" ) ) );
	}
	
	@Test
	void testEx4() {
		Assertions.assertNotNull( new ConfigException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	void testEx5() {
		Assertions.assertNotNull( ConfigException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	void testEx6() {
		Assertions.assertNotNull( ConfigException.convertEx( "g" , new ConfigException( "g" ) ) );
	}
	
	@Test
	void testEx7() {
		Assertions.assertNotNull( ConfigException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	void testEx8() {
		Assertions.assertNotNull( new ConfigException( 1 ) );
	}
	
	@Test
	void testEx9() {
		Assertions.assertNotNull( new ConfigException( "h", 2 ) );
	}
	
	@Test
	void testEx10() {
		Assertions.assertNotNull( new ConfigException( "i", new ConfigException( "l" ), 3 ) );
	}
	
	@Test
	void testEx11() {
		Assertions.assertNotNull( new ConfigException( new ConfigException( "m" ), 4 ) );
	}
	
	@Test
	void testEx12() throws ConfigException {
		Assertions.assertThrows( ConfigException.class, () -> ConfigException.stadardExceptionWrapping( new IOException( "n" ) ) );
	}
	
}
