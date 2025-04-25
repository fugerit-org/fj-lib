package test.org.fugerit.java.core.db.dao;

import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDAORuntimeException {
	
	@Test
	void testApply() {
		Assertions.assertThrows( DAORuntimeException.class ,() -> DAORuntimeException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	void testGet() {
		Assertions.assertThrows( DAORuntimeException.class ,() -> DAORuntimeException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	void testEx1() {
		Assertions.assertNotNull( new DAORuntimeException() );
	}
	
	@Test
	void testEx2() {
		Assertions.assertNotNull( new DAORuntimeException( "a" ) );
	}
	
	@Test
	void testEx3() {
		Assertions.assertNotNull( new DAORuntimeException( new SQLException( "b" ) ) );
	}
	
	@Test
	void testEx4() {
		Assertions.assertNotNull( new DAORuntimeException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	void testEx5() {
		Assertions.assertNotNull( DAORuntimeException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	void testEx6() {
		Assertions.assertNotNull( DAORuntimeException.convertEx( "g" , new DAORuntimeException( "g" ) ) );
	}
	
	@Test
	void testEx7() {
		Assertions.assertNotNull( DAORuntimeException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
}
