package test.org.fugerit.java.core.db.helpers;

import org.junit.BeforeClass;

import test.org.fugerit.java.MemDBJunitBase;

public class MemDBTestBase extends MemDBJunitBase {

	@BeforeClass
	public static void init() {
    	MemDBHelper.init();
	}
	
}
