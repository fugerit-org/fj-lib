package test.org.fugerit.java.core.db.helpers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestScriptFacade extends MemDBTestBase {

	public TestScriptFacade() throws Exception {
		super();
	}

	@Test
	void testSelect01() {
		boolean ok = false;
		try {
			this.simpleTestSelectWorker( "SELECT * FROM fugerit.user" );	
			ok = true;
		} catch (Exception e) {
			this.failEx(e);
		}
		assertTrue( ok );
	}
	
	@Test
	void testSelect02() {
		boolean ok = false;
		try {
			this.simpleTestSelectWorker( "SELECT * FROM fugerit.address" );	
			ok = true;
		} catch (Exception e) {
			this.failEx(e);
		}
		assertTrue( ok );
	}
	
}
