package test.org.fugerit.java.core.db.dao;

import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDAORuntimeException {
	
	@Test
	public void testApply() {
		Assertions.assertThrows( DAORuntimeException.class ,() -> DAORuntimeException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	public void testGet() {
		Assertions.assertThrows( DAORuntimeException.class ,() -> DAORuntimeException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	public void testEx1() {
		Assertions.assertNotNull( new DAORuntimeException() );
	}
	
	@Test
	public void testEx2() {
		Assertions.assertNotNull( new DAORuntimeException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assertions.assertNotNull( new DAORuntimeException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assertions.assertNotNull( new DAORuntimeException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	public void testEx5() {
		Assertions.assertNotNull( DAORuntimeException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assertions.assertNotNull( DAORuntimeException.convertEx( "g" , new DAORuntimeException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assertions.assertNotNull( DAORuntimeException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
}
