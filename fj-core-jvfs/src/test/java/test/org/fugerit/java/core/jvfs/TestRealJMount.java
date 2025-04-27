package test.org.fugerit.java.core.jvfs;

import org.fugerit.java.core.jvfs.JVFS;
import org.junit.jupiter.api.Test;

class TestRealJMount extends TestJVFSHelper {

	
	@Test
	 void testJVFSReal() {
		try {
			JVFS jvfs = TestJMountHelper.getRealJVFSDefault();
			this.testJVFSWorker(jvfs);
			this.testCreateDelete(jvfs);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
