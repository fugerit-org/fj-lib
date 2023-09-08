package test.org.fugerit.java.core.db.dao;

import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAOException;
import org.junit.Assert;
import org.junit.Test;

public class TestDAOException {

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
