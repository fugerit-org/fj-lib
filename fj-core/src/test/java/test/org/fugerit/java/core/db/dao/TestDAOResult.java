package test.org.fugerit.java.core.db.dao;

import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDAOResult {
	
	@Test
	void testOne() {
		BasicDaoResult<String> test = new BasicDaoResult<>();
		Assertions.assertNull( test.one() );
	}
	
}
