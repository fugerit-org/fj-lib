package test.org.fugerit.java.tool;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.tool.RunToolException;
import org.junit.Assert;
import org.junit.Test;

public class TestRunToolException {

	@Test
	public void testEx1() {
		Assert.assertNotNull( new RunToolException() );
	}
	
	@Test
	public void testEx2() {
		Assert.assertNotNull( new RunToolException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assert.assertNotNull( new RunToolException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assert.assertNotNull( new RunToolException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	public void testEx12() throws RunToolException {
		Assert.assertThrows( RunToolException.class, () -> RunToolException.stadardExceptionWrapping( new IOException( "n" ) ) );
	}
	
	@Test
	public void testEx5() {
		Assert.assertNotNull( RunToolException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assert.assertNotNull( RunToolException.convertEx( "g" , new RunToolException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assert.assertNotNull( RunToolException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
	
}
