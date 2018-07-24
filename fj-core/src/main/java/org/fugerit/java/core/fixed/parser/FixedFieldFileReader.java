package org.fugerit.java.core.fixed.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import org.fugerit.java.core.fixed.parser.helper.FixedFieldFileReaderAbstract;
import org.fugerit.java.core.fixed.parser.helper.ReaderFixedFieldFileReader;
import org.fugerit.java.core.fixed.parser.helper.StreamFixedFieldFileReader;

public class FixedFieldFileReader {
	
	private FixedFieldFileReaderAbstract readerImpl;
	
	public FixedFieldFileReader( FixedFieldFileDescriptor descriptor, InputStream is ) throws IOException {
		this.readerImpl = new StreamFixedFieldFileReader( descriptor, is );
	}
	
	public FixedFieldFileReader( FixedFieldFileDescriptor descriptor, Reader reader ) throws IOException {
		this.readerImpl = new ReaderFixedFieldFileReader( descriptor, reader );
	}

	public boolean hasNext() throws IOException {
		return this.readerImpl.hasNext();
	}
	
	public String nextLine() {
		return this.readerImpl.nextLine();
	}
	
	public FixedFileFieldMap nextRawMap() {
		return this.readerImpl.nextRawMap();
	}
	
	public List<FixedFileFieldValidationResult> getGenericValidationErrors() {
		return this.readerImpl.getGenericValidationErrors();
	}
	
	public void close() throws IOException {
		this.readerImpl.close();;
	}

	public FixedFieldFileDescriptor getDescriptor() {
		return this.readerImpl.getDescriptor();
	}

	public int getErrorCount() {
		return this.readerImpl.getErrorCount();
	}
	
}


