package test.org.fugerit.java.core.db.dao;

import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.junit.Assert;
import org.junit.Test;

public class TestDAOResult {
	
	@Test
	public void testOne() {
		BasicDaoResult<String> test = new BasicDaoResult<>();
		Assert.assertNull( test.one() );
	}
	
}
