package org.fugerit.java.core.fixed.parser;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.fugerit.java.core.fixed.parser.helper.FixedFieldFileReaderAbstract;
import org.fugerit.java.core.fixed.parser.helper.ReaderFixedFieldFileReader;
import org.fugerit.java.core.fixed.parser.helper.StreamFixedFieldFileReader;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class FixedFieldFileReader implements Closeable {
	
	private FixedFieldFileReaderAbstract readerImpl;
	
	public static final FixedFieldFileReader newInstance( FixedFieldFileDescriptor descriptor, Reader r ) throws IOException {
		FixedFieldFileReader reader = new FixedFieldFileReader( descriptor, r );
		return reader;
	}
	
	public static final FixedFieldFileReader newInstance( FixedFieldFileDescriptor descriptor, InputStream is ) throws IOException {
		String encoding = descriptor.getEncoding();
		FixedFieldFileReader reader = null;
		if ( StringUtils.isEmpty( encoding ) ) {
			reader = newInstance( descriptor, new InputStreamReader( is ) );
		} else {
			reader = newInstance( descriptor, new InputStreamReader( is, encoding ) );
		}
		return reader;
	}
	
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


