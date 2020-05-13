package test.org.fugerit.java.core.lang.binding;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.fugerit.java.core.lang.binding.BindingCatalogConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.org.fugerit.java.core.lang.helpers.reflect.TestModelOne;
import test.org.fugerit.java.core.lang.helpers.reflect.TestModelTwo;

public class TestBindingCatalog {

	private final static Logger logger = LoggerFactory.getLogger( TestBindingCatalog.class );
	
	
	private static BindingCatalogConfig init() {
		BindingCatalogConfig config = null;
		try {
			config = BindingCatalogConfig.loadConfig( ClassHelper.loadFromDefaultClassLoader( "core/lang/binding/binding-catalog.xml" ) );
		} catch (Exception e) {
			throw new RuntimeException( "Init error "+e, e );
		}
		return config;
	}
 	
	private final BindingCatalogConfig CATALOG = init();
	
	protected void bindWorder( String bindingId, Object from, Object to ) {
		try {
			logger.info( "test before -> {}", to );
			CATALOG.bind( bindingId , from, to );
			logger.info( "test after -> {}", to );
		} catch (Exception e) {
			String message = e.getMessage();
			logger.info( message, e );
			fail( message );
		}
	}
	
	private void bind001WOrker( String bindingId ) {
		BigDecimal testId = new BigDecimal( 100 );
		TestModelOne from = new TestModelOne();
		TestModelTwo kid = new TestModelTwo();
		kid.setIdTwo( testId );
		from.setKid( kid );
		TestModelOne to = new TestModelOne();
		this.bindWorder( bindingId , from, to);
		if ( !to.getIdOne().equals( testId ) ) {
			fail( "Wrong id with binding - "+bindingId );
		}
	}
	
	private void bind002WOrker( String bindingId ) {
		BigDecimal testId = new BigDecimal( 100 );
		TestModelOne from = new TestModelOne();
		from.setIdOne( testId );
		TestModelOne to = new TestModelOne();
		this.bindWorder( bindingId , from, to);
		if ( !to.getKid().getIdTwo().equals( testId ) ) {
			fail( "Wrong id with binding - "+bindingId );
		}
	}
	
	@Test
	public void bind001() {
		this.bind001WOrker( "binding-01" );
	}
	
	@Test
	public void bind001Def() {
		this.bind001WOrker( "binding-01-default" );
	}
	
	@Test
	public void bind002Def() {
		this.bind002WOrker( "binding-02-default" );
	}

	@Test
	public void bind003Def() {
		this.bind002WOrker( "binding-02-tryinit" );
	}
	
}
