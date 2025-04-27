package test.org.fugerit.java.tool;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.tool.RunToolException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestRunToolException {

	@Test
	void testEx1() {
		Assertions.assertNotNull( new RunToolException() );
	}
	
	@Test
	void testEx2() {
		Assertions.assertNotNull( new RunToolException( "a" ) );
	}
	
	@Test
	void testEx3() {
		Assertions.assertNotNull( new RunToolException( new SQLException( "b" ) ) );
	}
	
	@Test
	void testEx4() {
		Assertions.assertNotNull( new RunToolException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	void testEx12() throws RunToolException {
		Assertions.assertThrows( RunToolException.class, () -> RunToolException.stadardExceptionWrapping( new IOException( "n" ) ) );
	}
	
	@Test
	void testEx5() {
		Assertions.assertNotNull( RunToolException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	void testEx6() {
		Assertions.assertNotNull( RunToolException.convertEx( "g" , new RunToolException( "g" ) ) );
	}
	
	@Test
	void testEx7() {
		Assertions.assertNotNull( RunToolException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
	
}
