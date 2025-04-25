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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestStreamHelper {

	@Test
	void testStreamHelper() {
		SafeFunction.apply( () -> {
			Assertions.assertNotNull( new SimpleValue<Boolean>() );
			Assertions.assertFalse( StreamHelper.closeSafe( (InputStream) null ) );
			Assertions.assertFalse( StreamHelper.closeSafe( (Reader) null ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new ByteArrayInputStream( "a".getBytes() ) ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new StringReader( "b" ) ) );
			Assertions.assertFalse( StreamHelper.closeSafe( (OutputStream) null ) );
			Assertions.assertFalse( StreamHelper.closeSafe( (Writer) null ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new ByteArrayOutputStream() ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new ByteArrayOutputStream(), true ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new ByteArrayOutputStream(), false ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new StringWriter() ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new StringWriter(), true ) );
			Assertions.assertTrue( StreamHelper.closeSafe( new StringWriter(), false ) );
			Assertions.assertEquals( 1L , StreamHelper.pipe( new ByteArrayInputStream( "c".getBytes() ) , new ByteArrayOutputStream() ) );
			Assertions.assertEquals( 1L , StreamHelper.pipe( new StringReader( "d" ) , new StringWriter() ) );
			Assertions.assertEquals( 1L , StreamHelper.pipe( new ByteArrayInputStream( "c".getBytes() ) , new ByteArrayOutputStream() ), StreamIO.MODE_CLOSE_NONE );
			Assertions.assertEquals( 1L , StreamHelper.pipe( new StringReader( "d" ) , new StringWriter(), 512, StreamIO.MODE_CLOSE_NONE ) );
			File file = new File("src/test/resources/core/test_test.txt");
			Assertions.assertNotNull( StreamHelper.resolveStream( StreamHelper.PATH_FILE+file.getCanonicalPath(), null , null ) );
		} );
	}
	
}
