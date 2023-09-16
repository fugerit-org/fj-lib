package org.fugerit.java.core.fixed.parser.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.fixed.parser.FixedFieldDescriptor;
import org.fugerit.java.core.fixed.parser.FixedFieldFileDescriptor;
import org.fugerit.java.core.fixed.parser.FixedFileFieldBasicValidator;
import org.fugerit.java.core.fixed.parser.FixedFileFieldMap;
import org.fugerit.java.core.fixed.parser.FixedFileFieldValidationResult;
import org.fugerit.java.core.fixed.parser.FixedFileFieldValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class  FixedFieldFileReaderAbstract {
	
	protected static Logger logger = LoggerFactory.getLogger( FixedFieldFileReaderAbstract.class );
	
	private String endline;
	
	protected FixedFieldFileReaderAbstract( FixedFieldFileDescriptor descriptor ) throws IOException {
		this.descriptor = descriptor;
		this.bundle = FixedFileFieldBasicValidator.newBundle( this.getDescriptor().getBaseLocale() );
		if ( descriptor.isCustomEndlineActive() ) {
			String endlineLocal =  DEFAULT_MAPPER.getProperty( this.getDescriptor().getEndline() );
			if ( endlineLocal == null ) {
				throw new IOException( "Unsupported endline : "+this.getDescriptor().getEndline() );
			} else {
				this.endline = endlineLocal;
			}	
		} else {
			this.endline = null;
		}
		this.genericValidationErrors = new ArrayList<>();
	}
	
	public static final Properties endlineMapper() {
		Properties mapper = new Properties();
		mapper.setProperty( "\\r\\n" , "\r\n" );
		mapper.setProperty( "\\r" , "\r" );
		mapper.setProperty( "\\n" , "\n" );
		return mapper;
	}
	
	protected static String formatEndline( String el ) {
		return el.replace( "\n" , "\\n" ).replace( "\r" , "\\r" );
	}
	
	private List<FixedFileFieldValidationResult> genericValidationErrors;
	
	public List<FixedFileFieldValidationResult> getGenericValidationErrors() {
		return genericValidationErrors;
	}
	
	private static final Properties DEFAULT_MAPPER = endlineMapper();
	
	private ResourceBundle bundle;
	
	protected FixedFieldFileDescriptor descriptor;
	
	protected int rowNumber;
	
	protected int errorCount;
	
	public abstract boolean hasNext() throws IOException;
	
	public abstract void close() throws IOException;
	
	public abstract String nextLine();
	
	public FixedFileFieldMap nextRawMap() {
		FixedFileFieldMap rawMap = null;
		String currentLine = this.nextLine();
		if ( currentLine == null ) {
			throw new ConfigRuntimeException( "No line set" );
		} else {
			rawMap = new FixedFileFieldMap( currentLine.length(), this.rowNumber );
			Iterator<FixedFieldDescriptor> itFields = this.getDescriptor().getListFields().iterator();
			while ( itFields.hasNext() ) {
				FixedFieldDescriptor currentFieldDescriptor = itFields.next();
				String fieldId = currentFieldDescriptor.getNormalizedName();
				int start = currentFieldDescriptor.getStart();
				int end = currentFieldDescriptor.getEnd();
				String currentValue = currentLine.substring( start-1 , end-1 );
				rawMap.put( fieldId , currentValue );
				FixedFileFieldValidator validator = currentFieldDescriptor.getValidator();
				if ( validator != null ) {
					FixedFileFieldValidationResult result = validator.checkField( currentFieldDescriptor.getName() , currentValue, this.rowNumber, start );
					if ( !result.isValid() ) {
						rawMap.getValidationErrors().add( result );
						errorCount++;
					}
				}
			}	
		}
		return rawMap;
	}
	
	public FixedFieldFileDescriptor getDescriptor() {
		return descriptor;
	}
	
	public int getErrorCount() {
		return this.errorCount;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public String getEndline() {
		return endline;
	}

	protected void addEndlineError( String currentEndline ) {
		String message = FixedFileFieldBasicValidator.messageFormatWorker( this.getBundle(), "error.check.endline.value", formatEndline( currentEndline ), this.getDescriptor().getEndline(), rowNumber, this.rowNumber, "");
		FixedFileFieldValidationResult result = new FixedFileFieldValidationResult( false, this.getBundle().getString( "error.check.endline.label" ), message, null, this.rowNumber, 0 );
		this.getGenericValidationErrors().add( result );
		this.errorCount++;
	}
	
	protected void addRecordLenthError( int length  ) {
		String message = FixedFileFieldBasicValidator.messageFormatWorker( this.getBundle(), "error.check.line.length", String.valueOf( this.getDescriptor().getCheckLengh() ), String.valueOf( length ), rowNumber, this.rowNumber, "");
		FixedFileFieldValidationResult result = new FixedFileFieldValidationResult( false, this.getBundle().getString( "error.check.line.label" ), message, null, this.rowNumber, 0 );
		this.getGenericValidationErrors().add( result );
		this.errorCount++;
	}
	
}
