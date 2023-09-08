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
	
}
