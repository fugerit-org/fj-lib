package test.org.fugerit.java.core.io.line.helper;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.io.helper.HelperIOException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHelperIOEx {

	@Test
	public void testApplySilent() throws IOException {
		boolean ok = true;
		HelperIOException.applySilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testGetSilent() throws IOException {
		Object result = HelperIOException.getSilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assert.assertNull( result );
	}
	
	@Test
	public void testApplyEXMessage() {
		Assert.assertThrows( IOException.class ,() -> HelperIOException.applyWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}

	@Test
	public void testGetEXMessage() {
		Assert.assertThrows( IOException.class ,() -> HelperIOException.getWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}
	
	@Test
	public void testApplyEXMessageOk() throws IOException {
		boolean ok = true;
		HelperIOException.applyWithMessage( () -> log.info( "test ok" ) , "test message" );
		Assert.assertTrue( ok );
	}

	@Test
	public void testGetEXMessageOk() throws IOException {
		Assert.assertNotNull( HelperIOException.getWithMessage( () -> "test ok" , "test message" ) );
	}
	
	@Test
	public void testEx1() {
		Assert.assertNotNull( new HelperIOException() );
	}
	
	@Test
	public void testEx2() {
		Assert.assertNotNull( new HelperIOException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assert.assertNotNull( new HelperIOException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assert.assertNotNull( new HelperIOException( "c", new SQLException( "d" ) ) );
	}
	@Test
	public void testClose1() throws IOException {
		HelperIOException.close( (Closeable) () -> log.info( "Closeable.close()" ) );
		Assert.assertTrue( true );
	}

	@Test
	public void testClose2() throws IOException {
		HelperIOException.close( (AutoCloseable) () -> log.info( "AutoCloseable.close()" ) );
		Assert.assertTrue( true );
	}
	
	@Test
	public void testClose3() throws IOException {
		HelperIOException.close( (Closeable) null );
		Assert.assertTrue( true );
	}

	@Test
	public void testClose4() throws IOException {
		HelperIOException.close( (AutoCloseable) null );
		Assert.assertTrue( true );
	}
	
	@Test
	public void testApplyOk() throws IOException {
		HelperIOException.apply( () -> log.info( "test ok" )  );
		Assert.assertTrue( true );
	}

	@Test
	public void testGetOk() throws IOException {
		HelperIOException.get( () -> "ok" );
		Assert.assertTrue( true );
	}

	@Test
	public void testApply() {
		Assert.assertThrows( IOException.class ,() -> HelperIOException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	public void testGet() {
		Assert.assertThrows( IOException.class ,() -> HelperIOException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	public void testEx5() {
		Assert.assertNotNull( HelperIOException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assert.assertNotNull( HelperIOException.convertEx( "g" , new IOException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assert.assertNotNull( HelperIOException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}

	
}
