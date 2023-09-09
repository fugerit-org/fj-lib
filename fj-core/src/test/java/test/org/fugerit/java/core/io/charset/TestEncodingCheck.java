package test.org.fugerit.java.core.io.charset;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import org.fugerit.java.core.charset.EncodingCheck;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Assert;
import org.junit.Test;

public class TestEncodingCheck {

	@Test
	public void testEnc() throws IOException {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/xml/dtd/test-content-handler.xml" ) ) {
			boolean ok = EncodingCheck.checkEncoding( is , StandardCharsets.UTF_8.name() );
			Assert.assertTrue(ok);
		}
	}
	
	@Test
	public void testEncBytes() throws IOException {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/xml/dtd/test-content-handler.xml" ) ) {
			boolean ok = EncodingCheck.checkEncoding( StreamIO.readBytes(is) , StandardCharsets.UTF_8.name() );
			Assert.assertTrue(ok);
		}
	}
	
	@Test
	public void testEncByteWrong() throws IOException {
		String fileName = "src/test/resources/core/io/enc/test_no_utf8.gz";
		try  ( InputStream is = new GZIPInputStream( new FileInputStream( fileName ) ) ) {
			boolean ok = EncodingCheck.checkEncoding( StreamIO.readBytes(is) , StandardCharsets.UTF_8.name() );
			Assert.assertFalse(ok);
		}
	}
	
}
