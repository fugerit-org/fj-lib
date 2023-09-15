package test.org.fugerit.java.core.lang.helpers.reflect;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.reflect.FieldHelper;
import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class TestFieldHelper {

	private static final String TEST_VALUE = "test1";
	
	public class CheckBean {
		@Getter @Setter private String field1;
		public String field2;
	}
	
	@Test
	public void testSetField() {
		CheckBean bean = new CheckBean();
		Assert.assertNull( bean.getField1() );
		FieldHelper.setField(bean, "field1", TEST_VALUE, true);
		Assert.assertEquals( TEST_VALUE , FieldHelper.getField(bean, "field1", true) );
		FieldHelper.setField(bean, "field2", TEST_VALUE);
		Assert.assertEquals( TEST_VALUE , FieldHelper.getField(bean, "field2") );
		Assert.assertThrows( ConfigRuntimeException.class , () -> FieldHelper.setField(bean, "field1", TEST_VALUE) );
		Assert.assertThrows( ConfigRuntimeException.class , () -> FieldHelper.getField(bean, "field1") );
	}
	
}
