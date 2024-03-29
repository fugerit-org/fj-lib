package test.org.fugerit.java.core.lang.helpers.reflect;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.ListMapConfig;
import org.fugerit.java.core.lang.helpers.reflect.MethodHelper;
import org.junit.Assert;
import org.junit.Test;

public class TestMethodHelper {

	@Test
	public void testFoundMethod() {
		String id = "test-id";
		ListMapConfig<String> list = new ListMapConfig<String>();
		list.setId( id );
		String foundId = (String) MethodHelper.invokeGetter(  list , "id" );
		Assert.assertEquals( id , foundId );
	}
	
	@Test
	public void testNotFoundMethod() {
		ListMapConfig<String> list = new ListMapConfig<String>();
		Assert.assertThrows( ConfigRuntimeException.class , () ->  MethodHelper.invokeGetter(  list , "notExists" ) );
	}
	
}
