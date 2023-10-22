package test.org.fugerit.java.core.db.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAOException;
import org.junit.Assert;
import org.junit.Test;

public class TestDAOException {

	@Test
	public void testApplySilent() throws DAOException {
		boolean ok = true;
		DAOException.applySilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assert.assertTrue( ok );
	}
	
	@Test
	public void testGetSilent() throws DAOException {
		Object result = DAOException.getSilent( () -> { throw new IOException( "junit test scenario" ); } );
		Assert.assertNull( result );
	}
	
	@Test
	public void testApplyEXMessage() {
		Assert.assertThrows( DAOException.class ,() -> DAOException.applyWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}

	@Test
	public void testGetEXMessage() {
		Assert.assertThrows( DAOException.class ,() -> DAOException.getWithMessage( () -> { throw new IOException( "junit test scenario" ); }, "test message" ) );
	}
	
	@Test
	public void testApply() {
		Assert.assertThrows( DAOException.class ,() -> DAOException.apply( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}

	@Test
	public void testGet() {
		Assert.assertThrows( DAOException.class ,() -> DAOException.get( () -> { throw new SQLException( "junit test scenario" ); } ) );
	}
	
	@Test
	public void testEx1() {
		Assert.assertNotNull( new DAOException() );
	}
	
	@Test
	public void testEx2() {
		Assert.assertNotNull( new DAOException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assert.assertNotNull( new DAOException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assert.assertNotNull( new DAOException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	public void testEx5() {
		Assert.assertNotNull( DAOException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assert.assertNotNull( DAOException.convertEx( "g" , new DAOException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assert.assertNotNull( DAOException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
}
