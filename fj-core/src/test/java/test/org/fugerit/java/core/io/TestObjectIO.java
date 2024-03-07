package test.org.fugerit.java.core.io;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SimpleValue;
import org.fugerit.java.core.io.ObjectIO;
import org.fugerit.java.core.io.SafeIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Assert;
import org.junit.Test;
import test.org.fugerit.java.helpers.io.InputStreamFail;

import java.io.IOException;

public class TestObjectIO {

	private static final String PATH_OK = "core/xml/dtd/doc-1-0.dtd";
	
	@Test
	public void testStringOk() throws IOException {
		String test1 = "a";
		String test2 = (String)ObjectIO.fullSerializationTest( test1 );
		Assert.assertEquals( "a", test2 );
	}

	
}
