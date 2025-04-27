package test.org.fugerit.java.core.jvfs;

import java.io.File;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.junit.jupiter.api.Test;

class TestCopyClToReal extends TestJVFSHelper {

	@Test
	 void testCopy03() {
		try  {
			File dest = new File( "target/test_copy_02" );
			JVFS from = TestJMountHelper.getClassLoaderJVFSDefault();
			JVFS to =  RealJMount.createJVFS( dest );
			this.testWriteRead(from, to);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
