package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.junit.jupiter.api.Test;

class TestDeleteRealToReal extends TestJVFSHelper {

	@Test
	 void testDelete01() {
		try {
			File dest = new File( "target/test_delete_01" );
			logger.info( "create dest dir : {} -> {}", dest, dest.mkdirs() );
			JVFS from =  TestJMountHelper.getRealJVFSDefault();
			JVFS to =  RealJMount.createJVFS( dest );
			this.testWriteDelete(from, to);
		} catch (IOException e) {
			this.failEx(e);
		}
	}
	
}
