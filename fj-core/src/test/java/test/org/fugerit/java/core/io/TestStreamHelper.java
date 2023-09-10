package test.org.fugerit.java.core.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.SimpleValue;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.junit.Assert;
import org.junit.Test;

public class TestStreamHelper {

	@Test
	public void testStreamHelper() {
		SafeFunction.apply( () -> {
			Assert.assertNotNull( new SimpleValue<Boolean>() );
			Assert.assertFalse( StreamHelper.closeSafe( (InputStream) null ) );
			Assert.assertFalse( StreamHelper.closeSafe( (Reader) null ) );
			Assert.assertTrue( StreamHelper.closeSafe( new ByteArrayInputStream( "a".getBytes() ) ) );
			Assert.assertTrue( StreamHelper.closeSafe( new StringReader( "b" ) ) );
			Assert.assertFalse( StreamHelper.closeSafe( (OutputStream) null ) );
			Assert.assertFalse( StreamHelper.closeSafe( (Writer) null ) );
			Assert.assertTrue( StreamHelper.closeSafe( new ByteArrayOutputStream() ) );
			Assert.assertTrue( StreamHelper.closeSafe( new ByteArrayOutputStream(), true ) );
			Assert.assertTrue( StreamHelper.closeSafe( new ByteArrayOutputStream(), false ) );
			Assert.assertTrue( StreamHelper.closeSafe( new StringWriter() ) );
			Assert.assertTrue( StreamHelper.closeSafe( new StringWriter(), true ) );
			Assert.assertTrue( StreamHelper.closeSafe( new StringWriter(), false ) );
			Assert.assertEquals( 1L , StreamHelper.pipe( new ByteArrayInputStream( "c".getBytes() ) , new ByteArrayOutputStream() ) );
			Assert.assertEquals( 1L , StreamHelper.pipe( new StringReader( "d" ) , new StringWriter() ) );
			Assert.assertEquals( 1L , StreamHelper.pipe( new ByteArrayInputStream( "c".getBytes() ) , new ByteArrayOutputStream() ), StreamIO.MODE_CLOSE_NONE );
			Assert.assertEquals( 1L , StreamHelper.pipe( new StringReader( "d" ) , new StringWriter(), 512, StreamIO.MODE_CLOSE_NONE ) );
			File file = new File("src/test/resources/core/test_test.txt");
			Assert.assertNotNull( StreamHelper.resolveStream( StreamHelper.PATH_FILE+file.getCanonicalPath(), null , null ) );
		} );
	}
	
}
