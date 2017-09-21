package org.fugerit.java.core.fixed.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FixedFieldFileReader {

	private BufferedReader br;
	
	private String currentLine;
	
	private FixedFieldFileDescriptor descriptor;
	
	public FixedFieldFileReader( FixedFieldFileDescriptor descriptor, Reader reader ) {
		this.br = new BufferedReader( reader );
		this.descriptor = descriptor;
		this.currentLine = null;
	}
	
	public boolean hasNext() throws IOException {
		this.currentLine = this.br.readLine();
		return this.currentLine != null;
	}
	
	public String nextLine() {
		return this.currentLine;
	}
	
	public Map<String, String> nextRawMap() {
		HashMap<String, String> rawMap = new HashMap<String, String>();
		Iterator<FixedFieldDescriptor> itFields = this.getDescriptor().getListFields().iterator();
		while ( itFields.hasNext() ) {
			FixedFieldDescriptor currentFieldDescriptor = itFields.next();
			String fieldId = currentFieldDescriptor.getNormalizedName();
			int start = currentFieldDescriptor.getStart();
			int end = currentFieldDescriptor.getEnd();
			String currentValue = this.currentLine.substring( start-1 , end-1 );
			rawMap.put( fieldId , currentValue );
		}
		return rawMap;
	}
	
	public void close() throws IOException {
		this.br.close();
	}

	public FixedFieldFileDescriptor getDescriptor() {
		return descriptor;
	}
	
}
