package test.org.fugerit.java.core.io;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.helper.CustomPrintWriter;
import org.junit.Assert;
import org.junit.Test;

public class TestCustomPrintWriter {

	private boolean worker( CustomPrintWriter writer ) {
		boolean ok = false;
		writer.println( "test" );
		ok = true;
		return ok;
	}
	
	@Test
	public void test() {
		SafeFunction.apply( () -> {
			try ( CustomPrintWriter writer = new CustomPrintWriter( new ByteArrayOutputStream() ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}
			try ( CustomPrintWriter writer = new CustomPrintWriter( new ByteArrayOutputStream(), "\n" ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}
			try ( CustomPrintWriter writer = new CustomPrintWriter( new ByteArrayOutputStream(), false, "\n" ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}	
			try ( CustomPrintWriter writer = new CustomPrintWriter( new ByteArrayOutputStream(), true ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}
			try ( CustomPrintWriter writer = new CustomPrintWriter( new StringWriter() ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}
			try ( CustomPrintWriter writer = new CustomPrintWriter( new StringWriter(), "\n" ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}
			try ( CustomPrintWriter writer = new CustomPrintWriter( new StringWriter(), false, "\n" ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}
			try ( CustomPrintWriter writer = new CustomPrintWriter( new StringWriter(), true ) ) {
				boolean ok = this.worker(writer);
				Assert.assertTrue(ok);
			}
			// last test
			CustomPrintWriter writer = new CustomPrintWriter( new StringWriter(), true, "\n" );
			boolean ok = this.worker(writer);
			writer.setLineSeparator( "\n" );
			Assert.assertEquals( "\n", writer.getLineSeparator() );
			Assert.assertTrue(ok);
			writer.close();
			writer.println( "stop" );
		} );
	}
	
}
