package test.org.fugerit.java.core.xml.sax;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.XMLException;
import org.fugerit.java.core.xml.sax.XMLFactorySAX;
import org.fugerit.java.core.xml.sax.ch.ContentHandlerWrapper;
import org.fugerit.java.core.xml.sax.ch.ContentHandlerWriter;
import org.fugerit.java.core.xml.sax.ch.DoNothingContentHandler;
import org.fugerit.java.core.xml.sax.dh.DefaultHandlerComp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestContentHandler {

	private static final String PATH_OK = "core/xml/dtd/test-content-handler.xml";
	
	private boolean worker( String path, ContentHandler handler ) throws IOException, XMLException, SAXException {
		boolean ok = false;
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( path ) )  {
			SAXParser parser = XMLFactorySAX.makeSAXParser( false ,  true );
			parser.parse( is , new DefaultHandlerComp( handler ) );
			handler.processingInstruction( "a", "b" );	// metod invocation test
			handler.skippedEntity( "c" );	// metod invocation test
			handler.ignorableWhitespace(null, 0, 0); // metod invocation test
			ok = true;
		}
		return ok;
	}
	
	@Test
	void testContentHandlerWrapper() throws IOException, XMLException, SAXException {
		ContentHandlerWrapper handler = new ContentHandlerWrapper( new DoNothingContentHandler() );
		boolean ok = this.worker(PATH_OK, handler);
		Assertions.assertTrue(ok);
		try ( StringWriter buffer = new StringWriter();
				PrintWriter writer = new PrintWriter( buffer ) ) {
			handler.setWrappedContentHandler( new ContentHandlerWriter( writer ) );
			ok = this.worker(PATH_OK, handler);
			Assertions.assertTrue(ok);
			log.info( "xml : \n{}", buffer );
		}
	}
	
}
