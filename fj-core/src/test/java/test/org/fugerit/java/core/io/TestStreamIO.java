package test.org.fugerit.java.core.io;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;

public class TestStreamIO extends BasicTest {
	
	@Test
	void testStreamIO() {
		try {
			String path = "core/util/filterchain/minifilter-test-config.xml";
			InputStream is = ClassHelper.loadFromDefaultClassLoader( path );
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			StreamIO.pipeStream( is , os, StreamIO.MODE_CLOSE_BOTH );
			logger.info( "byte read : "+os.size() );
		} catch (Exception e) {
			String message = "Error : "+e;
			logger.error( message, e );
			fail( message );
		}
	}
	
}
