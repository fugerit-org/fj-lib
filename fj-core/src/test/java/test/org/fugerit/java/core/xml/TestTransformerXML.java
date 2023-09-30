package test.org.fugerit.java.core.xml;

import java.io.StringReader;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.xml.TransformerConfig;
import org.fugerit.java.core.xml.TransformerXML;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestTransformerXML extends BasicTest {
	
	private static final String SAMPLE_XLST = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"></xsl:stylesheet>";
	
	private static final String SAMPLE_XLST_FAIL = "<xsl:styleshee version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"></xsl:stylesheet>";
	
	@Test
	public void newSafeTransformerFactory() {
		TransformerFactory factory = TransformerXML.newSafeTransformerFactory();
		log.info( "factory -> {}", factory );
		Assert.assertNotNull( factory );
	}
	
	@Test
	public void newSafeTransformerFactoryFail() {
		boolean ok = false;
		try {
			Properties features = new Properties();
			features.setProperty( "fail-feature" , BooleanUtils.BOOLEAN_TRUE );
			TransformerFactory factory = TransformerXML.newSafeTransformerFactory( features );
			log.info( "factory -> {}", factory );
		} catch (ConfigRuntimeException e) {
			ok = true;
		}
		Assert.assertTrue( ok );
	}
	
	@Test
	public void newSafeTransformerFactoryFeature() {
		Properties features = new Properties();
		features.setProperty( XMLConstants.FEATURE_SECURE_PROCESSING , BooleanUtils.BOOLEAN_FALSE );
		TransformerFactory factory = TransformerXML.newSafeTransformerFactory( features );
		log.info( "factory -> {}", factory );
		Assert.assertNotNull( factory );
	}
	
	@Test
	public void newTransformer() {
		boolean ok = false;
		try {
			Transformer transformer = TransformerXML.newTransformer();
			log.info( "factory -> {}", transformer );
			ok = transformer != null;
		} catch (Exception e) {
			this.failEx(e);
		}
		Assert.assertTrue( ok );
	}
	
	@Test
	public void newTransformerConfig() {
		boolean ok = false;
		try {
			Transformer transformer = TransformerXML.newTransformerWithConfig( TransformerConfig.newIndentConfig(null) );
			log.info( "factory -> {}", transformer );
			ok = transformer != null;
		} catch (Exception e) {
			this.failEx(e);
		}
		Assert.assertTrue( ok );
	}
	
	@Test
	public void newTransformerSource() {
		boolean ok = false;
		try ( StringReader reader = new StringReader( SAMPLE_XLST ) ) {
			Transformer transformer = TransformerXML.newTransformer( new StreamSource( reader ) );
			log.info( "factory -> {}", transformer );
			ok = transformer != null;
		} catch (Exception e) {
			this.failEx(e);
		}
		Assert.assertTrue( ok );
	}
	
	@Test
	public void newTransformerSourceFail() {
		boolean ok = false;
		try ( StringReader reader = new StringReader( SAMPLE_XLST_FAIL ) ) {
			Transformer transformer = TransformerXML.newTransformer( new StreamSource( reader ) );
			log.info( "factory -> {}", transformer );
		} catch (Exception e) {
			ok = true;
		}
		Assert.assertTrue( ok );
	}
	
}
