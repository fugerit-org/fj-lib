package test.org.fugerit.java.core.io;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.io.SafeIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.helpers.io.InputStreamFail;

public class TestSafeIO {

	private static final String PATH_OK = "core/xml/dtd/doc-1-0.dtd";
	
	@Test
	public void testStringOk() {
		String res = SafeIO.readStringStream( () -> ClassHelper.loadFromDefaultClassLoader(PATH_OK) );
		Assert.assertNotNull( res );
	}
	
	@Test
	public void testBytesOk() {
		byte[] res = SafeIO.readBytes( () -> ClassHelper.loadFromDefaultClassLoader(PATH_OK) );
		Assert.assertNotNull( res );
	}
	
	@Test
	public void testStringKo() {
		Assert.assertThrows( ConfigRuntimeException.class , () -> SafeIO.readStringStream( () -> new InputStreamFail() ) );
	}
	
	@Test
	public void testBytesKo() {
		Assert.assertThrows( ConfigRuntimeException.class , () -> SafeIO.readBytes( () -> new InputStreamFail() ) );
	}
	
}
