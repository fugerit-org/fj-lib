package test.org.fugerit.java.core.xml.sax;

import java.util.Collection;

import org.fugerit.java.core.lang.helpers.ExHandler;
import org.fugerit.java.core.lang.helpers.ResultExHandler;
import org.fugerit.java.core.xml.sax.SAXErrorHandlerFail;
import org.fugerit.java.core.xml.sax.SAXErrorHandlerStore;
import org.fugerit.java.core.xml.sax.SAXParseResult;
import org.fugerit.java.core.xml.sax.dh.ExDefaultHandler;
import org.fugerit.java.core.xml.sax.eh.DoNothingErrorHandler;
import org.fugerit.java.core.xml.sax.eh.ErrorHandlerWrapper;
import org.fugerit.java.core.xml.sax.eh.ResultErrorHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.LocatorImpl;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
class TestErrorHandler extends BasicTest {

	private Locator newLocator( int row, int col ) {
		return new LocatorImpl() {
			@Override
			public int getLineNumber() {
				return row;
			}
			@Override
			public int getColumnNumber() {
				return col;
			}
		};
	}
	
	private SAXParseException newSPE( String message  ) {
		return new SAXParseException( message , this.newLocator( 2 , 11 ) );
	}
	
	private void print( Collection<SAXException> c, String type ) {
		for ( SAXException e : c ) {
			log.info( "{} -> {}", type, e.toString() );
		}
	}
	
	private boolean worker( ErrorHandler handler ) throws SAXException {
		boolean ok = false;
		handler.fatalError( this.newSPE( "Test fatal error" ) );
		handler.error( this.newSPE( "Test error" ) );
		handler.warning( this.newSPE( "Test warning" ) );
		ok = true;
		log.info( "ok : {}", ok );
		return ok;
	}
	
	@Test
	void testWrapper() throws SAXException {
		// test ErrorHandlerWrapper
		ErrorHandlerWrapper handler = new ErrorHandlerWrapper( new DoNothingErrorHandler() );
		boolean ok = this.worker(handler);
		Assertions.assertTrue( ok );
		// test ResultErrorHandler
		SAXParseResult saxParseResult = new SAXParseResult();
		ResultErrorHandler result = new ResultErrorHandler( saxParseResult );
		handler.setWrappedErrorHandler(result);
		ok = this.worker( result );
		log.info( "fatal : {}", saxParseResult.getSAXFatal( 0 ) );
		log.info( "error : {}", saxParseResult.getSAXError( 0 ) );
		log.info( "warning : {}", saxParseResult.getSAXWarning( 0 ) );
		Assertions.assertTrue( ok );
		// test ResultExHandler
		result.setHandler( new ResultExHandler( new SAXParseResult() ) );
		ok = this.worker( result );
		Assertions.assertTrue( ok );
		ExHandler exHandler = result.getHandler();
		Assertions.assertNotNull( exHandler );
		// test ExDefaultHandler
		ExDefaultHandler exDefaultHandler = new ExDefaultHandler( new ResultExHandler( new SAXParseResult() ) );
		ok = this.worker( exDefaultHandler );
		Assertions.assertTrue( ok );
		Assertions.assertNotNull( exDefaultHandler.getHandler() );
	}
	
	@Test
	void testSaxStore() throws SAXException {
		SAXErrorHandlerStore handler = new SAXErrorHandlerStore();
		boolean ok = this.worker(handler);
		log.info( "fatals size : {}", handler.getFatalSize() );
		this.print( handler.getErrors() , "FATALS" );
		log.info( "errors size : {}", handler.getErrorSize() );
		this.print( handler.getFatals() , "ERRORS" );
		log.info( "warnings size : {}", handler.getWarningSize() );
		this.print( handler.getWarnings() , "WARNINGS" );
		log.info( "all errors size : {}", handler.getAllErrorsSize() );
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testSaxFail() throws SAXException {
		SAXErrorHandlerFail handler = SAXErrorHandlerFail.newInstanceStoreOnly();
		boolean ok = this.worker(handler);
		Assertions.assertTrue( ok );
	}
	
	@Test
	void testSaxFailFatal() throws SAXException {
		SAXErrorHandlerFail handler = SAXErrorHandlerFail.newInstanceFailOnAnyErrorStoreWarnings();
		Assertions.assertThrows( SAXParseException.class , () -> 
			handler.fatalError(  this.newSPE( "newInstanceFailOnAnyErrorIgnoreWarnings fatal" ) ) );
		Assertions.assertThrows( SAXParseException.class , () -> 
			handler.error(  this.newSPE( "newInstanceFailOnAnyErrorIgnoreWarnings error" ) ) );		
	}
	
	@Test
	void testSaxFailError() throws SAXException {
		SAXErrorHandlerFail handler = SAXErrorHandlerFail.newInstanceFailOnFatalErrorStoreOthers();
		Assertions.assertThrows( SAXParseException.class , () -> 
			handler.fatalError(  this.newSPE( "newInstanceFailOnFatalErrorStoreOthers fatal" ) ) );
	}
	
	@Test
	void testSaxFailAll() throws SAXException {
		SAXErrorHandlerFail handler = SAXErrorHandlerFail.newInstanceFailOnAnyErrorIgnoreWarnings();
		handler.warning( this.newSPE( "newInstanceFailOnAnyErrorStoreWarnings warning" ) );
		Assertions.assertThrows( SAXParseException.class , () -> 
			handler.fatalError( this.newSPE( "newInstanceFailOnAnyErrorStoreWarnings fatal" ) ) );
		Assertions.assertThrows( SAXParseException.class , () -> 
			handler.error( this.newSPE( "newInstanceFailOnAnyErrorStoreWarnings error" ) ) );
	}
	
}
