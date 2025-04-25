package test.org.fugerit.java.core.io.line.helper;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.io.helper.HelperIOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHelperIOEx {

	@Test
	public void testApplySilent() throws IOException {
		boolean ok = true;
		HelperIOException.applySilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertTrue( ok );
	}
	
	@Test
	public void testGetSilent() throws IOException {
		Object result = HelperIOException.getSilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertNull( result );
	}
	
	@Test
	public void testApplyEXMessage() {
		Assertions.assertThrows( IOException.class ,() -> HelperIOException.applyWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}

	@Test
	public void testGetEXMessage() {
		Assertions.assertThrows( IOException.class ,() -> HelperIOException.getWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}
	
	@Test
	public void testApplyEXMessageOk() throws IOException {
		boolean ok = true;
		HelperIOException.applyWithMessage( () -> log.info( "test ok" ) , "test message" );
		Assertions.assertTrue( ok );
	}

	@Test
	public void testGetEXMessageOk() throws IOException {
		Assertions.assertNotNull( HelperIOException.getWithMessage( () -> "test ok" , "test message" ) );
	}
	
	@Test
	public void testEx1() {
		Assertions.assertNotNull( new HelperIOException() );
	}
	
	@Test
	public void testEx2() {
		Assertions.assertNotNull( new HelperIOException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assertions.assertNotNull( new HelperIOException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assertions.assertNotNull( new HelperIOException( "c", new SQLException( "d" ) ) );
	}
	@Test
	public void testClose1() throws IOException {
		HelperIOException.close( (Closeable) () -> log.info( "Closeable.close()" ) );
		Assertions.assertTrue( true );
	}

	@Test
	public void testClose2() throws IOException {
		HelperIOException.close( (AutoCloseable) () -> log.info( "AutoCloseable.close()" ) );
		Assertions.assertTrue( true );
	}
	
	@Test
	public void testClose3() throws IOException {
		HelperIOException.close( (Closeable) null );
		Assertions.assertTrue( true );
	}

	@Test
	public void testClose4() throws IOException {
		HelperIOException.close( (AutoCloseable) null );
		Assertions.assertTrue( true );
	}
	
	@Test
	public void testApplyOk() throws IOException {
		HelperIOException.apply( () -> log.info( "test ok" )  );
		Assertions.assertTrue( true );
	}

	@Test
	public void testGetOk() throws IOException {
		HelperIOException.get( () -> "ok" );
		Assertions.assertTrue( true );
	}

	@Test
	public void testApply() {
		Assertions.assertThrows( IOException.class ,() -> HelperIOException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	public void testGet() {
		Assertions.assertThrows( IOException.class ,() -> HelperIOException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	public void testEx5() {
		Assertions.assertNotNull( HelperIOException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assertions.assertNotNull( HelperIOException.convertEx( "g" , new IOException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assertions.assertNotNull( HelperIOException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}

	
}
