package org.fugerit.java.core.fixed.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class FixedFieldFileReader {

	public static final Properties endlineMapper() {
		Properties mapper = new Properties();
		mapper.setProperty( "\\r\\n" , "\r\n" );
		mapper.setProperty( "\\r" , "\r" );
		mapper.setProperty( "\\n" , "\n" );
		return mapper;
	}
	
	private static String formatEndline( String el ) {
		return el.replace( "\n" , "\\n" ).replace( "\r" , "\\r" );
	}
	
	private static final Properties DEFAULT_MAPPER = endlineMapper();
	
	private BufferedReader br;
	
	private String currentLine;
	
	private FixedFieldFileDescriptor descriptor;
	
	private int rowNumber;
	
	private int errorCount;
	
	private String endline;
	
	private String currentEndline;
	
	private ResourceBundle bundle;
	
	private List<FixedFileFieldValidationResult> genericValidationErrors;
	
	public FixedFieldFileReader( FixedFieldFileDescriptor descriptor, Reader reader ) throws IOException {
		this.br = new BufferedReader( reader );
		this.descriptor = descriptor;
		this.currentLine = null;
		this.rowNumber = 0;
		this.bundle = FixedFileFieldBasicValidator.newBundle( this.getDescriptor().getBaseLocale() );
		if ( descriptor.isCustomEndlineActive() ) {
			String endline =  DEFAULT_MAPPER.getProperty( this.getDescriptor().getEndline() );
			if ( endline == null ) {
				throw new IOException( "Unsupported endline : "+this.getDescriptor().getEndline() );
			} else {
				this.endline = endline;
			}	
		} else {
			this.endline = null;
		}
		this.genericValidationErrors = new ArrayList<FixedFileFieldValidationResult>();
	}
	
	public List<FixedFileFieldValidationResult> getGenericValidationErrors() {
		return genericValidationErrors;
	}

	public boolean hasNext() throws IOException {
		if ( this.getDescriptor().isCustomEndlineActive() ) {
			char[] buffer = new char[ this.getDescriptor().getCheckLengh() + this.endline.length() ];
			int read = this.br.read( buffer );
			if ( read >= 0 ) {
				this.currentLine = new String( buffer, 0, buffer.length-this.endline.length() );
				this.currentEndline = new String( buffer, buffer.length-this.endline.length(), this.endline.length() );
				
				if ( this.getDescriptor().isCustomEndlineActive() ) {
					if ( this.currentLine.length() != this.getDescriptor().getCheckLengh() ) {
						String message = FixedFileFieldBasicValidator.messageFormatWorker( bundle, "error.check.line.length", String.valueOf( this.getDescriptor().getCheckLengh() ), String.valueOf( this.currentLine.length() ), rowNumber, this.rowNumber, "");
						FixedFileFieldValidationResult result = new FixedFileFieldValidationResult( false, bundle.getString( "error.check.line.label" ), message, null, this.rowNumber, 0 );
						this.getGenericValidationErrors().add( result );
					}
					if ( !this.endline.equals( this.currentEndline ) ) {
						String message = FixedFileFieldBasicValidator.messageFormatWorker( bundle, "error.check.endline.value", formatEndline( this.currentEndline ), this.getDescriptor().getEndline(), rowNumber, this.rowNumber, "");
						FixedFileFieldValidationResult result = new FixedFileFieldValidationResult( false, bundle.getString( "error.check.endline.label" ), message, null, this.rowNumber, 0 );
						this.getGenericValidationErrors().add( result );
						this.errorCount++;
					} 
					if ( this.getGenericValidationErrors().size() > 0 ) {
						this.currentLine = null;
					}
				}
				
			} else {
				this.currentLine = null;
				this.currentEndline = null;
			}
		} else {
			this.currentLine = this.br.readLine();
		}
		if ( this.currentLine != null ) {
			this.rowNumber++;
		}
		return this.currentLine != null;
	}
	
	public String nextLine() {
		return this.currentLine;
	}
	
	public FixedFileFieldMap nextRawMap() {
		FixedFileFieldMap rawMap = null;
		if ( this.currentLine == null ) {
			throw new RuntimeException( "No line set" );
		} else {
			rawMap = new FixedFileFieldMap( this.currentLine.length(), this.rowNumber );
			
			Iterator<FixedFieldDescriptor> itFields = this.getDescriptor().getListFields().iterator();
			while ( itFields.hasNext() ) {
				FixedFieldDescriptor currentFieldDescriptor = itFields.next();
				String fieldId = currentFieldDescriptor.getNormalizedName();
				int start = currentFieldDescriptor.getStart();
				int end = currentFieldDescriptor.getEnd();
				String currentValue = this.currentLine.substring( start-1 , end-1 );
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
	
	public void close() throws IOException {
		this.br.close();
	}

	public FixedFieldFileDescriptor getDescriptor() {
		return descriptor;
	}

	public int getErrorCount() {
		return errorCount;
	}
	
}

