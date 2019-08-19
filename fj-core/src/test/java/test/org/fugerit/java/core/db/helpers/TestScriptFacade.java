package test.org.fugerit.java.core.db.helpers;

import org.junit.Test;

import test.org.fugerit.java.MemDBTestBase;

public class TestScriptFacade extends MemDBTestBase {

	public TestScriptFacade() throws Exception {
		super();
	}
	

	
	@Test
	public void testSelect01() {
		this.simpleTestSelectWorker( "SELECT * FROM fugerit.user" );
	}
	
	@Test
	public void testSelect02() {
		this.simpleTestSelectWorker( "SELECT * FROM fugerit.address" );
	}
	
}
