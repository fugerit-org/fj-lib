package test.org.fugerit.java.core.lang.binding;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.lang.binding.*;

@Slf4j
public class BindingHelper extends BindingHelperDefault {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6082079779742598082L;

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
	}

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
	}
	
}
