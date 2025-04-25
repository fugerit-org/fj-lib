package test.org.fugerit.java.core.io;

import org.fugerit.java.core.function.SimpleValue;
import org.fugerit.java.core.io.ObjectIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestObjectIO {

	@Test
	public void testStringOk() throws IOException {
		String test1 = "a";
		String test2 = (String)ObjectIO.fullSerializationTest( test1 );
		Assertions.assertEquals( "a", test2 );
	}

	@Test
	public void testKo() throws IOException {
		SimpleValue val = new SimpleValue( "b" );
		Assertions.assertThrows( IOException.class, () -> ObjectIO.fullSerializationTest( val ) );
	}
	
}
