package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.net.URI;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.junit.Test;

public class TestUriFileCLMount extends TestJVFSHelper {

	@Test
	public void testJVFSReal() {
		try {
			URI uri = Thread.currentThread().getContextClassLoader().getResource( "core-jvfs" ).toURI();
			File root = new File( uri );
			JVFS jvfs =  RealJMount.createJVFS( root );
			this.testJVFSWorker(jvfs);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
