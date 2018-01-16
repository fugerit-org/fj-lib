package org.fugerit.java.core.fixed.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class FixedFieldFileReader {

	private BufferedReader br;
	
	private String currentLine;
	
	private FixedFieldFileDescriptor descriptor;
	
	private int rowNumber;
	
	private int errorCount;
	
	public FixedFieldFileReader( FixedFieldFileDescriptor descriptor, Reader reader ) {
		this.br = new BufferedReader( reader );
		this.descriptor = descriptor;
		this.currentLine = null;
		this.rowNumber = 0;
	}
	
	public boolean hasNext() throws IOException {
		this.currentLine = this.br.readLine();
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
			rawMap = new FixedFileFieldMap( this.currentLine.length() );
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
