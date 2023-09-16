package org.fugerit.java.core.xml.sax;

import java.util.ArrayList;
import java.util.Collection;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SAXErrorHandlerStore implements ErrorHandler {

	private Collection<SAXException> errors;
	private Collection<SAXException> fatals;
	private Collection<SAXException> warnings;
	
	public SAXErrorHandlerStore() {
		this.errors = new ArrayList<>();
		this.fatals = new ArrayList<>();
		this.warnings = new ArrayList<>();
	}
	
	@Override
	public void error(SAXParseException e) throws SAXException {
		this.errors.add( e );
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		this.fatals.add( e );
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		this.warnings.add( e );
	}

	public Collection<SAXException> getErrors() {
		return errors;
	}

	public Collection<SAXException> getFatals() {
		return fatals;
	}

	public Collection<SAXException> getWarnings() {
		return warnings;
	}

	public int getErrorSize() {
		return this.getErrors().size();
	}
	
	public int getFatalSize() {
		return this.getFatals().size();
	}
	
	public int getWarningSize() {
		return this.getWarnings().size();
	}
	
	public int getAllErrorsSize() {
		return this.getErrorSize() + this.getFatalSize();
	}
	
}