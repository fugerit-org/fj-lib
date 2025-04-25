package test.org.fugerit.java.core.xml.dom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.io.FileIO;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;
import test.org.fugerit.java.core.testhelpers.FailOutputStream;

@Slf4j
public class TestDOMIO extends BasicTest {

	private static final String XML_TEST_PATH = "src/test/resources/core/cfg/xml/property-catalog-test.xml";
	
	private static final String XML_TEST_PATH_KO = "src/test/resources/core/cfg/xml/props/props-01.properties";

	private static final EntityResolver ENTITY_RESOLVER = new EntityResolver() {
		@Override
		public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
			// do nothing entity resolver
			return null;
		}
	};
	
	@Test
	void testNewDocumentBuilderFactoryFail() {
		boolean ok = false;
		try {
			Properties features = new Properties();
			features.setProperty( "test" , BooleanUtils.BOOLEAN_FALSE );
			DocumentBuilderFactory dbf = DOMIO.newSafeDocumentBuilderFactory( features );
			log.info( "DOMIO.newDocumentBuilderFactory (features) -> {}", dbf );
		} catch (ConfigRuntimeException e) {
			log.warn( "Exception OK : {}", e.toString() );
			ok = true;
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testNewDocumentBuilderFactoryOk() {
		boolean ok = false;
		try {
			Properties features = new Properties();
			features.setProperty( "http://xml.org/sax/features/external-general-entities" , BooleanUtils.BOOLEAN_FALSE );
			features.setProperty( "http://xml.org/sax/features/external-general-entities" , BooleanUtils.BOOLEAN_FALSE );
			DocumentBuilderFactory dbf = DOMIO.newSafeDocumentBuilderFactory( features );
			log.info( "DOMIO.newDocumentBuilderFactory (features) -> {}", dbf );
			ok = dbf != null;
		} catch (ConfigRuntimeException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testNewUnsafeDocumentBuilderFactory() {
		DocumentBuilderFactory dbf = DOMIO.newDocumentBuilderFactory( true );
		log.info( "DOMIO.newDocumentBuilderFactory -> {}", dbf );
		Assertions.assertNotNull( dbf );
	}
	
	
	@Test
	void testNewSafeDocumentBuilderFactory() {
		DocumentBuilderFactory dbf = DOMIO.newSafeDocumentBuilderFactory();
		log.info( "DOMIO.newSafeDocumentBuilderFactory -> {}", dbf );
		Assertions.assertNotNull( dbf );
	}
	
	@Test
	void testLoadDOMDocString() {
		boolean ok = false;
		try {
			ok = DOMIO.loadDOMDoc( FileIO.readString( new File( XML_TEST_PATH ) ) ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}

	@Test
	void testLoadDOMDocSource() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH ) ) {
			ok = DOMIO.loadDOMDoc( new InputSource(is) ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocSourceNsa() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH ) ) {
			ok = DOMIO.loadDOMDoc( new InputSource(is), true ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocSourceEr() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH ) ) {
			ok = DOMIO.loadDOMDoc( new InputSource(is), ENTITY_RESOLVER ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocSourceErNsa() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH ) ) {
			ok = DOMIO.loadDOMDoc( new InputSource(is), ENTITY_RESOLVER, true ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocStream() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH ) ) {
			ok = DOMIO.loadDOMDoc( is ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocReader() {
		boolean ok = false;
		try ( Reader reader = new InputStreamReader( new FileInputStream( XML_TEST_PATH ) ) ) {
			ok = DOMIO.loadDOMDoc( reader ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocNsaReader() {
		boolean ok = false;
		try ( Reader reader = new InputStreamReader( new FileInputStream( XML_TEST_PATH ) ) ) {
			ok = DOMIO.loadDOMDoc( reader, false ) != null;
		} catch (IOException | XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocFile() {
		boolean ok = false;
		try {
			ok = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) ) != null;
		} catch (XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocNsaFile() {
		boolean ok = false;
		try {
			ok = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ), false ) != null;
		} catch (XMLException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocStringFail() {
		boolean ok = false;
		try {
			DOMIO.loadDOMDoc( FileIO.readString( new File( XML_TEST_PATH_KO ) ) );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}	
	
	@Test
	void testLoadDOMDocSourceFail() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH_KO ) ) {
			DOMIO.loadDOMDoc( new InputSource(is) );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocSourceErFail() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH_KO ) ) {
			DOMIO.loadDOMDoc( new InputSource(is), ENTITY_RESOLVER );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocSourceErNsaFail() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH_KO ) ) {
			DOMIO.loadDOMDoc( new InputSource(is), ENTITY_RESOLVER, true );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}	
	
	@Test
	void testLoadDOMDocSourceNsaFail() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH_KO ) ) {
			DOMIO.loadDOMDoc( new InputSource(is), false );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocStreamFail() {
		boolean ok = false;
		try ( InputStream is = new FileInputStream( XML_TEST_PATH_KO ) ) {
			DOMIO.loadDOMDoc( is );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocReaderFail() {
		boolean ok = false;
		try ( Reader reader = new InputStreamReader( new FileInputStream( XML_TEST_PATH_KO ) ) ) {
			DOMIO.loadDOMDoc( reader );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocFileFail() {
		boolean ok = false;
		try {
			DOMIO.loadDOMDoc( new File( XML_TEST_PATH_KO ) );
		} catch (XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocReaderNsaFail() {
		boolean ok = false;
		try ( Reader reader = new InputStreamReader( new FileInputStream( XML_TEST_PATH_KO ) ) ) {
			DOMIO.loadDOMDoc( reader, false );
		} catch (IOException | XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocFileNsaFail() {
		boolean ok = false;
		try {
			DOMIO.loadDOMDoc( new File( XML_TEST_PATH_KO ), false );
		} catch (XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocFileNotFoundFail() {
		boolean ok = false;
		try {
			DOMIO.loadDOMDoc( new File( "fadsfasfdasdas" ) );
		} catch (XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testLoadDOMDocFileNsaNotFoundFail() {
		boolean ok = false;
		try {
			DOMIO.loadDOMDoc( new File( "fadsfasfdasdas" ), false );
		} catch (XMLException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMIndentResult() {
		boolean ok = false;
		try ( StringWriter sw = new StringWriter() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOMIndent( doc.getDocumentElement() , new StreamResult( sw ) , 2 );
			String result = sw.toString();
			log.info( "result -> {}", result );
			ok = result.length() > 0;
		} catch (XMLException | IOException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMIndentResultFailIOExepction() {
		boolean ok = false;
		try ( FailOutputStream os = new FailOutputStream() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOMIndent( doc.getDocumentElement() , new StreamResult( os ) , 2 );
		} catch (XMLException | IOException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMIndentResultFail() {
		boolean ok = false;
		try ( StringWriter sw = new StringWriter() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH_KO ) );
			DOMIO.writeDOMIndent( doc.getDocumentElement() , new StreamResult( sw ) , 2 );
			String result = sw.toString();
			log.info( "result -> {}", result );
		} catch (XMLException | IOException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMIndentWriter() {
		boolean ok = false;
		try ( StringWriter sw = new StringWriter() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOMIndent( doc.getDocumentElement() , sw , 2 );
			String result = sw.toString();
			log.info( "result -> {}", result );
			ok = result.length() > 0;
		} catch (XMLException | IOException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMIndentStream() {
		boolean ok = false;
		try ( ByteArrayOutputStream os = new ByteArrayOutputStream() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOMIndent( doc.getDocumentElement() , os );
			String result = String.valueOf( os.toByteArray() );
			log.info( "result -> {}", result );
			ok = result.length() > 0;
		} catch (XMLException | IOException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMWriter() {
		boolean ok = false;
		try ( StringWriter sw = new StringWriter() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOMIndent( doc.getDocumentElement() , sw );
			String result = sw.toString();
			log.info( "result -> {}", result );
			ok = result.length() > 0;
		} catch (XMLException | IOException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMResult() {
		boolean ok = false;
		try ( StringWriter sw = new StringWriter() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOMIndent( doc.getDocumentElement() , new StreamResult( sw ) );
			String result = sw.toString();
			log.info( "result -> {}", result );
			ok = result.length() > 0;
		} catch (XMLException | IOException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMStream() {
		boolean ok = false;
		try ( ByteArrayOutputStream os = new ByteArrayOutputStream() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOM( doc.getDocumentElement() , os );
			String result = String.valueOf( os.toByteArray() );
			log.info( "result -> {}", result );
			ok = result.length() > 0;
		} catch (XMLException | IOException e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMStreamFail() {
		boolean ok = false;
		try ( ByteArrayOutputStream os = new ByteArrayOutputStream() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH_KO ) );
			DOMIO.writeDOM( doc.getDocumentElement() , os );
		} catch (XMLException | IOException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testWriteDOMStreamFailStream() {
		boolean ok = false;
		try ( OutputStream os = new FailOutputStream() ) {
			Document doc = DOMIO.loadDOMDoc( new File( XML_TEST_PATH ) );
			DOMIO.writeDOM( doc.getDocumentElement() , os );
		} catch (XMLException | IOException e) {
			log.info( "Exception : {}", e.toString() );
			ok = ( e instanceof XMLException );
		}
		Assertions.assertTrue( ok );
	}
		
}
