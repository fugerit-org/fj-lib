package org.fugerit.java.core.xml.sax;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author mttfranci
 *
 */
@Slf4j
public class SAXErrorHandlerFail extends SAXErrorHandlerStore {

	public static final int MODE_FAIL_ON_ANY_ERROR_STORE_WARNINGS = 2;
	public static final int MODE_FAIL_ON_ANY_ERROR_IGNORE_WARNINGS = 3;
	public static final int MODE_DEFAULT = MODE_FAIL_ON_ANY_ERROR_STORE_WARNINGS;
	
	public static SAXErrorHandlerFail newInstanceStoreOnly() {
		return new SAXErrorHandlerFail();
	}
	
	public static SAXErrorHandlerFail newInstanceFailOnAnyErrorStoreWarnings() {
		return new SAXErrorHandlerFail() {
			@Override
			public void error(SAXParseException e) throws SAXException {
				super.error(e);
				throw e;
			}
			@Override
			public void fatalError(SAXParseException e) throws SAXException {
				super.fatalError(e);
				throw e;
			}
		};
	}

	public static SAXErrorHandlerFail newInstanceFailOnFatalErrorStoreOthers() {
		return new SAXErrorHandlerFail() {
			@Override
			public void fatalError(SAXParseException e) throws SAXException {
				super.fatalError(e);
				throw e;
			}
		};
	}
	
	public static SAXErrorHandlerFail newInstanceFailOnAnyErrorIgnoreWarnings() {
		return new SAXErrorHandlerFail() {
			@Override
			public void error(SAXParseException e) throws SAXException {
				super.error(e);
				throw e;
			}
			@Override
			public void fatalError(SAXParseException e) throws SAXException {
				super.fatalError(e);
				throw e;
			}
			@Override
			public void warning(SAXParseException e) throws SAXException {
				log.trace( "warning : {}", e.toString() );
			}
		};
	}
	
}
