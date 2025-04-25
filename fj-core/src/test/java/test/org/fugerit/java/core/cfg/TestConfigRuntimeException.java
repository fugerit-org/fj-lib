package test.org.fugerit.java.core.cfg;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestConfigRuntimeException {
	
	private static final IOException TEST_IO_EX = new IOException( "test io ex" );
	
	@Test
	void testEx1() {
		Assertions.assertNotNull( new ConfigRuntimeException() );
	}
	
	@Test
	void testEx2() {
		Assertions.assertNotNull( new ConfigRuntimeException( "a" ) );
	}
	
	@Test
	void testEx3() {
		Assertions.assertNotNull( new ConfigRuntimeException( new SQLException( "b" ) ) );
	}
	
	@Test
	void testEx4() {
		Assertions.assertNotNull( new ConfigRuntimeException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	void testEx5() {
		Assertions.assertNotNull( ConfigRuntimeException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	void testEx6() {
		Assertions.assertNotNull( ConfigRuntimeException.convertEx( "g" , new ConfigRuntimeException( "g" ) ) );
	}
	
	@Test
	void testEx7() {
		Assertions.assertNotNull( ConfigRuntimeException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}

	@Test
	void testEx21() {
		Assertions.assertNotNull( ConfigRuntimeException.convertToRuntimeEx( new ConfigRuntimeException( "y" ) ) );
	}


	@Test
	void testEx22() {
		Assertions.assertNotNull( ConfigRuntimeException.convertToRuntimeEx( new IOException( "z" ) ) );
	}
	
	@Test
	void testEx8() {
		Assertions.assertNotNull( new ConfigRuntimeException( 1 ) );
	}
	
	@Test
	void testEx9() {
		Assertions.assertNotNull( new ConfigRuntimeException( "h", 2 ) );
	}
	
	@Test
	void testEx10() {
		Assertions.assertNotNull( new ConfigRuntimeException( "i", new ConfigRuntimeException( "l" ), 3 ) );
	}
	
	@Test
	void testEx11() {
		Assertions.assertNotNull( new ConfigRuntimeException( new ConfigRuntimeException( "m" ), 4 ) );
	}
	
	@Test
	void testEx12() throws ConfigRuntimeException {
		Assertions.assertThrows( ConfigRuntimeException.class, () -> ConfigRuntimeException.stadardExceptionWrapping( TEST_IO_EX ) );
	}
	
}
