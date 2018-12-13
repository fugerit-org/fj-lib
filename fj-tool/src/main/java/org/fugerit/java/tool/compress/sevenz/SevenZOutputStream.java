package org.fugerit.java.tool.compress.sevenz;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

public class SevenZOutputStream extends OutputStream {

	public SevenZOutputStream(SevenZOutputFile archiveFile) {
		super();
		this.archiveFile = archiveFile;
	}

	private SevenZOutputFile archiveFile;
	
	@Override
	public void write(int b) throws IOException {
		this.archiveFile.write( b );
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.archiveFile.write( b );
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.archiveFile.write(b, off, len);
	}

	@Override
	public void close() throws IOException {
		this.archiveFile.close();
	}

	public SevenZOutputFile getArchiveFile() {
		return archiveFile;
	}

}
