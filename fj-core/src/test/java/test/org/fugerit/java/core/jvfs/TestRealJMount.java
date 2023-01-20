package test.org.fugerit.java.core.jvfs;

import java.io.File;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.junit.Test;

public class TestRealJMount extends TestJVFSHelper {

	
	@Test
	public void testJVFSReal() {
		try {
			JVFS jvfs =  RealJMount.createJVFS( new File( "src/test/resources/core" ) );
			this.testJVFSWorker(jvfs);
			this.testCreateDelete(jvfs);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
