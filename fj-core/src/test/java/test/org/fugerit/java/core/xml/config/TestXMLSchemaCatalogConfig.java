package test.org.fugerit.java.core.xml.config;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.GenericListCatalogConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.config.FugeritXmlSchemaCatalogConfig;
import org.fugerit.java.core.xml.config.XMLSchemaCatalogConfig;
import org.fugerit.java.core.xml.sax.SAXErrorHandlerStore;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

import test.org.fugerit.java.BasicTest;

public class TestXMLSchemaCatalogConfig extends BasicTest {

	private static final String SCHEMA_ID_DEFAULT = "current";	// default schema catalog id
	
	private static final String PATH_XML_OK = "core/xml/config/xml_schema_catalog/samples/doc_test_01.xml";
	
	private static final String PATH_XML_KO = "core/xml/config/xml_schema_catalog/samples/doc_test_02_ko.xml";

	private static XMLSchemaCatalogConfig loadCatalogSafe( String path ) {
		XMLSchemaCatalogConfig current = new XMLSchemaCatalogConfig();
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( path ) ) {
			current.setDefinition( FugeritXmlSchemaCatalogConfig.getInstance() );
			current.setSchemaId( "schema-catalog-config-current" );
			GenericListCatalogConfig.load( is, current);
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "init" , e );
		}
		return current;
	}
	
	private static XMLSchemaCatalogConfig catalog = loadCatalogSafe( "core/xml/config/xml_schema_catalog/schema-validator-config.xml" );
		
	@Test
	public void testLoad() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			Assert.assertNotNull( XMLSchemaCatalogConfig.loadConfigSchema( is ) );
		} catch (Exception e) {
			this.failEx(e);
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
	
	@Test
	public void testSerialization() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( PATH_XML_OK ) ) {
			XMLSchemaCatalogConfig loadedCatalog = (XMLSchemaCatalogConfig)this.fullSerializationTest( catalog );
			SAXErrorHandlerStore result = new SAXErrorHandlerStore();
			loadedCatalog.validate(result, new StreamSource( is ), SCHEMA_ID_DEFAULT );
			Assert.assertEquals( 0, result.getAllErrorsSize() );
		} catch (Exception e) {
			this.failEx(e);
		}		
	}
	
	@Test
	public void activateValidation() {
		XMLSchemaCatalogConfig current = loadCatalogSafe( "core/xml/config/xml_schema_catalog/schema-validator-config_fail1.xml" );
		logger.info( "activateValidation() catalog loaded : {}", current );
		Assert.assertNotNull( current );
	}
	
	@Test
	public void newInstanceException() {
		Assert.assertThrows( ConfigRuntimeException.class , () -> {
			XMLSchemaCatalogConfig current = loadCatalogSafe( "core/xml/config/xml_schema_catalog/schema-validator-config_fail2.xml" );
			logger.info( "activateValidation() catalog loaded : {}", current );	
		});
	}
	
	@Test
	public void newInstanceExceptionDuplicate() {
		Assert.assertThrows( ConfigRuntimeException.class , () -> {
			XMLSchemaCatalogConfig current = loadCatalogSafe( "core/xml/config/xml_schema_catalog/schema-validator-config_fail3.xml" );
			logger.info( "activateValidation() catalog loaded : {}", current );
		});
	}
	
}
