package test.org.fugerit.java.core.db.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDAOException {

	@Test
	void testApplySilent() throws DAOException {
		boolean ok = true;
		DAOException.applySilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testGetSilent() throws DAOException {
		Object result = DAOException.getSilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertNull( result );
	}
	
	@Test
	void testApplyEXMessage() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.applyWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}

	@Test
	void testGetEXMessage() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.getWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}
	
	@Test
	void testApplyEXMessageOk() throws DAOException {
		boolean ok = true;
		DAOException.applyWithMessage( () -> log.info( "test ok" ) , "test message" );
		Assertions.assertTrue( ok );
	}

	@Test
	void testGetEXMessageOk() throws DAOException {
		Assertions.assertNotNull( DAOException.getWithMessage( () -> "test ok" , "test message" ) );
	}
	
	@Test
	void testApply() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	void testGet() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	void testEx1() {
		Assertions.assertNotNull( new DAOException() );
	}
	
	@Test
	void testEx2() {
		Assertions.assertNotNull( new DAOException( "a" ) );
	}
	
	@Test
	void testEx3() {
		Assertions.assertNotNull( new DAOException( new SQLException( "b" ) ) );
	}
	
	@Test
	void testEx4() {
		Assertions.assertNotNull( new DAOException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	void testEx5() {
		Assertions.assertNotNull( DAOException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	void testEx6() {
		Assertions.assertNotNull( DAOException.convertEx( "g" , new DAOException( "g" ) ) );
	}
	
	@Test
	void testEx7() {
		Assertions.assertNotNull( DAOException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
}
