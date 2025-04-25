package test.org.fugerit.java.core.lang.helpers.reflect;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.ListMapConfig;
import org.fugerit.java.core.lang.helpers.reflect.MethodHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMethodHelper {

	@Test
	void testFoundMethod() {
		String id = "test-id";
		ListMapConfig<String> list = new ListMapConfig<String>();
		list.setId( id );
		String foundId = (String) MethodHelper.invokeGetter(  list , "id" );
		Assertions.assertEquals( id , foundId );
	}
	
	@Test
	void testNotFoundMethod() {
		ListMapConfig<String> list = new ListMapConfig<String>();
		Assertions.assertThrows( ConfigRuntimeException.class , () ->  MethodHelper.invokeGetter(  list , "notExists" ) );
	}
	
}
