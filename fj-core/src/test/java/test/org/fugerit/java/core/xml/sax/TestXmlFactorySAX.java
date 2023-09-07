package test.org.fugerit.java.core.xml.sax;

import org.fugerit.java.core.xml.FeatureUtils;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.sax.XMLFactorySAX;
import org.fugerit.java.core.xml.sax.dh.DefaultHandlerComp;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestXmlFactorySAX {

	private boolean worker( XMLFactorySAX factory ) throws XMLException {
		boolean ok = factory != null;
		if ( ok ) {
			Assert.assertNotNull( factory.newSAXParser() );
			Assert.assertNotNull( factory.newXMLValidator() );
			Assert.assertNotNull( factory.newXMLValidator( DefaultHandlerComp.DEFAULT_ER ) );
			log.info( "isNamespaceAware {}, isValidating {}", factory.isNamespaceAware(), factory.isValidating() );
		}
		return ok;
	}
	
	@Test
	public void test1() throws XMLException {
		boolean ok = this.worker( XMLFactorySAX.newInstance() );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void test2() throws XMLException {
		boolean ok = this.worker( XMLFactorySAX.newInstance( true ) );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void test3() throws XMLException {
		boolean ok = this.worker( XMLFactorySAX.newInstance( false ) );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testFinal() throws XMLException {
		XMLFactorySAX factory = XMLFactorySAX.newInstance();
		factory.setNamespaceAware( true );
		factory.setValidating( true );
		factory.setFeature( FeatureUtils.EXTERNAL_GENERAL_ENTITIES, false );
		factory.setFeature( FeatureUtils.EXTERNAL_PARAMETER_ENTITIES, false );
		log.info( "feature {} - {}", FeatureUtils.EXTERNAL_GENERAL_ENTITIES, factory.getFeature( FeatureUtils.EXTERNAL_GENERAL_ENTITIES ) );
		boolean ok = this.worker( factory );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testEx() throws XMLException {
		XMLFactorySAX factory = XMLFactorySAX.newInstance();
		Assert.assertThrows( XMLException.class, () -> factory.getFeature( "test" ) );
		Assert.assertThrows( XMLException.class, () -> factory.setFeature( "test", true ) );
	}
	
}
