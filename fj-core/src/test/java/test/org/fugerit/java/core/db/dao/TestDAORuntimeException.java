package test.org.fugerit.java.core.db.dao;

import java.sql.SQLException;

import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.junit.Assert;
import org.junit.Test;

public class TestDAORuntimeException {
	
	@Test
	public void testEx1() {
		Assert.assertNotNull( new DAORuntimeException() );
	}
	
	@Test
	public void testEx2() {
		Assert.assertNotNull( new DAORuntimeException( "a" ) );
	}
	
	@Test
	public void testEx3() {
		Assert.assertNotNull( new DAORuntimeException( new SQLException( "b" ) ) );
	}
	
	@Test
	public void testEx4() {
		Assert.assertNotNull( new DAORuntimeException( "c", new SQLException( "d" ) ) );
	}
	
	@Test
	public void testEx5() {
		Assert.assertNotNull( DAORuntimeException.convertEx( "e" , new SQLException( "f" ) ) );
	}
	
	@Test
	public void testEx6() {
		Assert.assertNotNull( DAORuntimeException.convertEx( "g" , new DAORuntimeException( "g" ) ) );
	}
	
	@Test
	public void testEx7() {
		Assert.assertNotNull( DAORuntimeException.convertExMethod( "e" , new SQLException( "f" ) ) );
	}
	
}
