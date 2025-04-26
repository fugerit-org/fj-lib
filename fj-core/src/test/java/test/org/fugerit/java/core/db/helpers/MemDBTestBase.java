package test.org.fugerit.java.core.db.helpers;

import org.junit.jupiter.api.BeforeAll;
import test.org.fugerit.java.MemDBJunitBase;

public class MemDBTestBase extends MemDBJunitBase {

	@BeforeAll
	 static void init() {
    	MemDBHelper.init();
	}
	
}
