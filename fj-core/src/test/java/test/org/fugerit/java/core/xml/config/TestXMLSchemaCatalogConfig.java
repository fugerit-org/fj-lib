package test.org.fugerit.java.core.xml.config;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.config.XMLSchemaCatalogConfig;
import org.fugerit.java.core.xml.sax.SAXErrorHandlerStore;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;

import test.org.fugerit.java.BasicTest;

public class TestXMLSchemaCatalogConfig extends BasicTest {

	private static final String SCHEMA_ID_DEFAULT = "current";	// default schema catalog id
	
	private static final String PATH_XML_OK = "core/xml/config/xml_schema_catalog/samples/doc_test_01.xml";
	
	private static final String PATH_XML_KO = "core/xml/config/xml_schema_catalog/samples/doc_test_02_ko.xml";
	
	private static XMLSchemaCatalogConfig catalog;
	
	@BeforeClass
	public static void init() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/xml/config/xml_schema_catalog/schema-validator-config.xml" ) ) {
			catalog = XMLSchemaCatalogConfig.loadConfigSchema( is );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "init" , e );
		}
	}
	
	@Test
	public void testValidateOK() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			catalog.validate(result, new StreamSource( is ), SCHEMA_ID_DEFAULT );
			Assert.assertEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testValidateSaxOK() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			catalog.validate(result, new SAXSource( new InputSource( is ) ), SCHEMA_ID_DEFAULT );
			Assert.assertEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testValidateCacheOK() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			catalog.validateCacheSchema(result, new StreamSource( is ), SCHEMA_ID_DEFAULT );
			Assert.assertEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testValidateSaxCacheOK() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			catalog.validateCacheSchema(result, new SAXSource( new InputSource( is ) ), SCHEMA_ID_DEFAULT );
			Assert.assertEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testValidateKo() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_KO ) ) {
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			catalog.validate(result, new StreamSource( is ), SCHEMA_ID_DEFAULT );
			Assert.assertNotEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testValidateXsdsSaxOK() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			Source[] xsds = catalog.getXsds( SCHEMA_ID_DEFAULT );
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			XMLSchemaCatalogConfig.validateWorker(result, new SAXSource( new InputSource( is ) ), xsds );
			Assert.assertEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testValidateXsdsOK() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			Source[] xsds = XMLSchemaCatalogConfig.getXsds( XMLSchemaCatalogConfig.ATT_TAG_MODULE_CONF_MODE_FILE, 
					"src/test/resources/core/xml/config/xml_schema_catalog/xsds", catalog.getDataList( SCHEMA_ID_DEFAULT ) );
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			XMLSchemaCatalogConfig.validateWorker(result, new StreamSource( is ), xsds );
			Assert.assertEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
