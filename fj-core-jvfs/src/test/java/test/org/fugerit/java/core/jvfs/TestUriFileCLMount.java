package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.cl.UriFileClJMount;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestUriFileCLMount extends TestJVFSHelper {

	@Test
	 void testJVFSReal() {
		try {
			URI uri = Thread.currentThread().getContextClassLoader().getResource( "core-jvfs" ).toURI();
			File root = new File( uri );
			JVFS jvfs =  RealJMount.createJVFS( root );
			this.testJVFSWorker(jvfs);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	 void testToFile() throws IOException {
		File file = UriFileClJMount.toFile( "org/fugerit/java/core/jvfs/db" );
		log.info( "test -> {}", file );
	}
	
}
