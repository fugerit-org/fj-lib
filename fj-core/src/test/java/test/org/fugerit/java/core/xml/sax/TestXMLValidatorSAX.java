package test.org.fugerit.java.core.xml.sax;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.fugerit.java.core.io.SafeIO;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.sax.SAXParseResult;
import org.fugerit.java.core.xml.sax.XMLValidatorSAX;
import org.fugerit.java.core.xml.sax.er.ByteArrayEntityResolver;
import org.fugerit.java.core.xml.sax.er.CharArrayEntityResolver;
import org.fugerit.java.core.xml.sax.er.DoNothingEntityResolver;
import org.fugerit.java.core.xml.sax.er.EntityResolverWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestXMLValidatorSAX extends BasicTest {

	private static final String PATH_OK = "core/xml/dtd/doc-ok.xml";
	
	private static final String PATH_OK_PUBLICID = "core/xml/dtd/doc-ok-publicid.xml";
	
	private static final String PATH_KO = "core/xml/dtd/doc-ko.xml";
	
	private static final String DTD_PATH = "core/xml/dtd/doc-1-0.dtd";
	
	private static final byte[] DTD_DATA = SafeIO.readBytes( () -> ClassHelper.loadFromDefaultClassLoader( DTD_PATH ) );
	
	private static final String DTD_DATA_ALT = SafeIO.readStringStream( () -> ClassHelper.loadFromDefaultClassLoader( "core/xml/dtd/doc-1-0.dtd" ) );
	
	private static final String DTD_PUBLIC_ID = "doc";
	
	private static final String DTD_SYSTEM_ID = "http://javacoredoc.fugerit.org";
	
	private static final EntityResolver DTD_DEFAULT_RESOLVER = new ByteArrayEntityResolver(DTD_DATA, DTD_PUBLIC_ID, DTD_SYSTEM_ID);
	
	private static final EntityResolver DTD_DEFAULT_RESOLVER_ALT = new CharArrayEntityResolver( DTD_DATA_ALT.toCharArray(), DTD_PUBLIC_ID, DTD_SYSTEM_ID);
	
	private void printResult( Collection<Exception> c, String type ) {
		for ( Exception e : c ) {
			log.info( "{} - {}", type, e.toString() );
		}
	}
	
	private void printResult(SAXParseResult result) {
		printResult( result.fatals() ,"FATALS" );
		printResult( result.errors() ,"ERRORS" );
		printResult( result.warnings() ,"WARNINGS" );
		log.info( "result.isPartialSuccess() {}", result.isPartialSuccess() );
	}
	
	private boolean validateWorker( EntityResolver er, String path ) throws XMLException, IOException {
		boolean ok = false;
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( er, true );
		try ( InputStreamReader reader = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( path ) ) ) {
			SAXParseResult result = new SAXParseResult();
			ok = validator.validateXML( reader, result );
			this.printResult(result);
		}
		return ok;
	}
		
	@Test
	public void testAbstractValidatorStringXml() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertNotNull( validator.validateXML( StreamIO.readString( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) );
	}
	
	@Test
	public void testAbstractValidatorStringValid() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertTrue( validator.isValidaXML( StreamIO.readString( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) );
	}
	
	@Test
	public void testAbstractValidatorStringValidResult() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertTrue( validator.validateXML( StreamIO.readString( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ), new SAXParseResult() ) );
	}
	
	@Test
	public void testAbstractValidatorCharXml() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertNotNull( validator.validateXML( StreamIO.readString( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ).toCharArray() ) );
	}
	
	@Test
	public void testAbstractValidatorCharValid() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertTrue( validator.isValidaXML( StreamIO.readString( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ).toCharArray() ) );
	}
	
	@Test
	public void testAbstractValidatorCharValidResult() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertTrue( validator.validateXML( StreamIO.readString( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ).toCharArray(), new SAXParseResult() ) );
	}
	
	@Test
	public void testAbstractValidatorByteXml() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertNotNull( validator.validateXML( StreamIO.readBytes( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) );
	}
	
	@Test
	public void testAbstractValidatorBytesValid() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertTrue( validator.isValidaXML( StreamIO.readBytes( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) );
	}
	
	@Test
	public void testAbstractValidatorBytesValidResult() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		Assertions.assertTrue( validator.validateXML( StreamIO.readBytes( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ), new SAXParseResult() ) );
	}
	
	@Test
	public void testAbstractValidatorStreamXml() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStream source =  ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) {
			Assertions.assertNotNull( validator.validateXML( source ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorStreamValid() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStream source =  ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) {
			Assertions.assertTrue( validator.isValidaXML( source ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorStreamValidResult() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStream source =  ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) {
			Assertions.assertTrue( validator.validateXML( source, new SAXParseResult() ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorReaderXml() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStreamReader source = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) {
			Assertions.assertNotNull( validator.validateXML( source ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorReaderValid() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStreamReader source = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) {
			Assertions.assertTrue( validator.isValidaXML( source ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorReaderValidResult() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStreamReader source = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) {
			Assertions.assertTrue( validator.validateXML( source, new SAXParseResult() ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorSourceXml() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStreamReader source = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) {
			Assertions.assertNotNull( validator.validateXML( new InputSource( source ) ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorSourceValid() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStreamReader source = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) {
			Assertions.assertTrue( validator.isValidaXML( new InputSource( source ) ) );	
		}
	}
	
	@Test
	public void testAbstractValidatorSourceValidResult() throws XMLException, IOException {
		XMLValidatorSAX validator = XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER, true );
		try ( InputStreamReader source = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( PATH_OK ) ) ) {
			Assertions.assertTrue( validator.validateXML( new InputSource( source ), new SAXParseResult() ) );	
		}
	}
	
	@Test
	public void testValidationOk1() throws XMLException, IOException {
		boolean valid = this.validateWorker(DTD_DEFAULT_RESOLVER,PATH_OK);
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void testValidationKo1() throws XMLException, IOException {
		boolean valid = this.validateWorker(DTD_DEFAULT_RESOLVER,PATH_KO);
		Assertions.assertFalse(valid);
	}
	
	@Test
	public void testEntityResolverWrapper1() throws XMLException, IOException {
		EntityResolverWrapper er = new EntityResolverWrapper(DTD_DEFAULT_RESOLVER);
		er.setWrappedEntityResolver(DTD_DEFAULT_RESOLVER_ALT);
		boolean valid = this.validateWorker(er,PATH_OK);
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void testEntityResolverWrapper2() throws XMLException, IOException {
		EntityResolverWrapper er = new EntityResolverWrapper(DTD_DEFAULT_RESOLVER) {
		    @Override
		    public InputSource resolveEntity(String arg0, String arg1)
		            throws SAXException, IOException {
		    	return super.resolveEntity( null, arg1 );
		    }
		};
		boolean valid = this.validateWorker(er,PATH_OK);
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void testEntityResolverWrapper3() throws XMLException, IOException {
		EntityResolverWrapper er = new EntityResolverWrapper(DTD_DEFAULT_RESOLVER) {
		    @Override
		    public InputSource resolveEntity(String arg0, String arg1)
		            throws SAXException, IOException {
		    	return super.resolveEntity( arg0, null );
		    }
		};
		boolean valid = this.validateWorker(er,PATH_OK_PUBLICID);
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void testEntityResolverWrapper4() throws XMLException, IOException {
		EntityResolverWrapper er = new EntityResolverWrapper(DTD_DEFAULT_RESOLVER_ALT) {
		    @Override
		    public InputSource resolveEntity(String arg0, String arg1)
		            throws SAXException, IOException {
		    	return super.resolveEntity( arg0, null );
		    }
		};
		boolean valid = this.validateWorker(er,PATH_OK_PUBLICID);
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void testEntityResolverWrapper5() throws XMLException, IOException {
		Assertions.assertThrows( Exception.class , () -> this.validateWorker(new DoNothingEntityResolver(),PATH_OK) );
	}
	
	@Test
	public void testEntityResolverWrapper6() throws XMLException, IOException {
		EntityResolverWrapper er = new EntityResolverWrapper(DTD_DEFAULT_RESOLVER_ALT) {
		    @Override
		    public InputSource resolveEntity(String arg0, String arg1)
		            throws SAXException, IOException {
		    	return super.resolveEntity( null, arg1 );
		    }
		};
		boolean valid = this.validateWorker(er,PATH_OK);
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void testNewInstance() throws XMLException {
		Assertions.assertNotNull( XMLValidatorSAX.newInstance() );
		Assertions.assertNotNull( XMLValidatorSAX.newInstance( true ) );
		Assertions.assertNotNull( XMLValidatorSAX.newInstance( false ) );
		Assertions.assertNotNull( XMLValidatorSAX.newInstance( DTD_DEFAULT_RESOLVER ) );
	}
	
}
