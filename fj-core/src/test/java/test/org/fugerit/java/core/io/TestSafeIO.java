package test.org.fugerit.java.core.io;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.io.SafeIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.helpers.io.InputStreamFail;

class TestSafeIO {

	private static final String PATH_OK = "core/xml/dtd/doc-1-0.dtd";
	
	@Test
	void testStringOk() {
		String res = SafeIO.readStringStream( () -> ClassHelper.loadFromDefaultClassLoader(PATH_OK) );
		Assertions.assertNotNull( res );
	}
	
	@Test
	void testBytesOk() {
		byte[] res = SafeIO.readBytes( () -> ClassHelper.loadFromDefaultClassLoader(PATH_OK) );
		Assertions.assertNotNull( res );
	}
	
	@Test
	void testStringKo() {
		Assertions.assertThrows( ConfigRuntimeException.class , () -> SafeIO.readStringStream( () -> new InputStreamFail() ) );
	}
	
	@Test
	void testBytesKo() {
		Assertions.assertThrows( ConfigRuntimeException.class , () -> SafeIO.readBytes( () -> new InputStreamFail() ) );
	}
	
}
