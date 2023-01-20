package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.junit.Test;

public class TestDeleteRealToReal extends TestJVFSHelper {

	@Test
	public void testDelete01() {
		try {
			File dest = new File( "target/test_delete_01" );
			logger.info( "create dest dir : {} -> {}", dest, dest.mkdirs() );
			JVFS from =  RealJMount.createJVFS( new File( "src/test/resources/core" ) );
			JVFS to =  RealJMount.createJVFS( dest );
			this.testWriteDelete(from, to);
		} catch (IOException e) {
			this.failEx(e);
		}
	}
	
}
