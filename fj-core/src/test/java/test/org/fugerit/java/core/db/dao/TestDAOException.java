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
	public void testApplySilent() throws DAOException {
		boolean ok = true;
		DAOException.applySilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertTrue( ok );
	}
	
	@Test
	public void testGetSilent() throws DAOException {
		Object result = DAOException.getSilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assertions.assertNull( result );
	}
	
	@Test
	public void testApplyEXMessage() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.applyWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}

	@Test
	public void testGetEXMessage() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.getWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}
	
	@Test
	public void testApplyEXMessageOk() throws DAOException {
		boolean ok = true;
		DAOException.applyWithMessage( () -> log.info( "test ok" ) , "test message" );
		Assertions.assertTrue( ok );
	}

	@Test
	public void testGetEXMessageOk() throws DAOException {
		Assertions.assertNotNull( DAOException.getWithMessage( () -> "test ok" , "test message" ) );
	}
	
	@Test
	public void testApply() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	public void testGet() {
		Assertions.assertThrows( DAOException.class ,() -> DAOException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	public void testEx1() {
		Assertions.assertNotNull( new DAOException() );
	}
	
	@Test
	public void testEx2() {
		Assertions.assertNotNull( new DAOException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assertions.assertNotNull( new DAOException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assertions.assertNotNull( new DAOException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	public void testEx5() {
		Assertions.assertNotNull( DAOException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assertions.assertNotNull( DAOException.convertEx( "g" , new DAOException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assertions.assertNotNull( DAOException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
}
