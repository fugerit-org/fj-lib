package test.org.fugerit.java.core.lang.binding;

import org.fugerit.java.core.lang.binding.BindingConfig;
import org.fugerit.java.core.lang.binding.BindingContext;
import org.fugerit.java.core.lang.binding.BindingException;
import org.fugerit.java.core.lang.binding.BindingFieldConfig;
import org.fugerit.java.core.lang.binding.BindingHelperDefault;
import org.fugerit.java.core.lang.binding.BindingHelperInitTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestBindingHelper extends BindingHelperDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6082079779742598082L;

	@Test
	public void testBindingHelperInitTo1() throws BindingException {
		BindingContext context = new BindingContext();
		BindingFieldConfig fieldConfig = new BindingFieldConfig();
		BindingConfig bindingConfig = new BindingConfig();
		fieldConfig.setBindFrom( "fieldOne" );
		fieldConfig.setBindTo( "fieldTwo" );
		ModelTestBindingOne from = new ModelTestBindingOne();
		ModelTestBindingTwo to = new ModelTestBindingTwo();
		BindingHelperInitTo.DEFAULT.bind(context, bindingConfig, fieldConfig, from, to);
		log.info( "to.fieldTwo : {}", to.getFieldTwo() );
		Assertions.assertNotNull( to.getFieldTwo() );
	}
	
	@Test
	public void testBindingHelperInitTo2() throws BindingException {
		BindingContext context = new BindingContext();
		BindingFieldConfig fieldConfig = new BindingFieldConfig();
		BindingConfig bindingConfig = new BindingConfig();
		fieldConfig.setBindFrom( "testHelperOne" );
		fieldConfig.setBindTo( "testHelperTwo" );
		fieldConfig.setTypeTo( ModelTestHelperImpl.class.getName() );
		ModelTestBindingOne from = new ModelTestBindingOne();
		ModelTestBindingTwo to = new ModelTestBindingTwo();
		BindingHelperInitTo.DEFAULT.bind(context, bindingConfig, fieldConfig, from, to);
		log.info( "to.testHelperTwo : {}", to.getTestHelperTwo() );
		Assertions.assertNotNull( to.getTestHelperTwo() );
	}
	
}
