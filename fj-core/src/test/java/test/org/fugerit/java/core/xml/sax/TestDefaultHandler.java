package test.org.fugerit.java.core.xml.sax;

import java.io.IOException;

import org.fugerit.java.core.xml.sax.dh.DefaultHandlerComp;
import org.fugerit.java.core.xml.sax.dtd.DTDHandlerWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.helpers.FailHelper;

@Slf4j
public class TestDefaultHandler {

	private boolean worker( DefaultHandler dh ) {
		log.info( "default handler -> {}", dh );
		if ( dh != null && dh instanceof DefaultHandlerComp ) {
			DefaultHandlerComp comp = (DefaultHandlerComp) dh;
			log.info( "dh : {}, er : {}, ch : {}, eh : {}", 
					comp.getWrappedDTDHandler(), comp.getWrappedEntityResolver(), 
					comp.getWrappedContentHandler(), comp.getWrappedErrorHandler() );
		}
		return dh != null; 
	}
	
	@Test
	void defaultHandlerTest0() {
		DefaultHandlerComp dh = new DefaultHandlerComp();
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest1() {
		DefaultHandlerComp dh = new DefaultHandlerComp( DefaultHandlerComp.DEFAULT_CH );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest2() {
		DefaultHandlerComp dh = new DefaultHandlerComp( DefaultHandlerComp.DEFAULT_DH );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest3() {
		DefaultHandlerComp dh = new DefaultHandlerComp( DefaultHandlerComp.DEFAULT_EH );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest4() {
		DefaultHandlerComp dh = new DefaultHandlerComp( DefaultHandlerComp.DEFAULT_ER );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest5() {
		DefaultHandlerComp dh = new DefaultHandlerComp( 
				DefaultHandlerComp.DEFAULT_DH, DefaultHandlerComp.DEFAULT_ER, DefaultHandlerComp.DEFAULT_EH );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest6() {
		DefaultHandlerComp dh = new DefaultHandlerComp( 
				DefaultHandlerComp.DEFAULT_CH, DefaultHandlerComp.DEFAULT_ER, DefaultHandlerComp.DEFAULT_EH );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest7() {
		DefaultHandlerComp dh = new DefaultHandlerComp( 
				DefaultHandlerComp.DEFAULT_CH, DefaultHandlerComp.DEFAULT_DH, DefaultHandlerComp.DEFAULT_EH );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTest8() {
		DefaultHandlerComp dh = new DefaultHandlerComp( 
				DefaultHandlerComp.DEFAULT_CH, DefaultHandlerComp.DEFAULT_DH, DefaultHandlerComp.DEFAULT_ER );
		boolean ok = this.worker(dh);
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTestFinal() throws SAXException {
		DefaultHandlerComp dh = new DefaultHandlerComp();
		DTDHandlerWrapper dtdDtdHandlerWrapper = new DTDHandlerWrapper( DefaultHandlerComp.DEFAULT_DH );
		dh.setWrappedContentHandler( DefaultHandlerComp.DEFAULT_CH );
		dh.setWrappedDTDHandler( dtdDtdHandlerWrapper );
		dh.setWrappedEntityResolver( DefaultHandlerComp.DEFAULT_ER );
		dh.setWrappedErrorHandler( DefaultHandlerComp.DEFAULT_EH );
		boolean ok = this.worker(dh);
		dh.endPrefixMapping( "e" );
		dh.processingInstruction( "a" , "b" );
		dh.skippedEntity( "c" );
		dh.startPrefixMapping( "f" , "g" );
		dh.notationDecl( "h", "i", "l");
		dh.unparsedEntityDecl( "m", "n", "o", "p" );
		dh.fatalError( null );
		dh.warning( null );
		Assertions.assertTrue(ok);
	}
	
	@Test
	void defaultHandlerTestKo() {
		DefaultHandlerComp dh = new DefaultHandlerComp( new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				if ( FailHelper.DO_FAIL ) {
					throw new IOException( "Scenaio fail" );
				}
				return null;
			}
		} );
		Assertions.assertThrows( SAXException.class , () -> dh.resolveEntity(null, null) );
	}
	
}
