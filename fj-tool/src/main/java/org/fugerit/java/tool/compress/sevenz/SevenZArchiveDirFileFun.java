package org.fugerit.java.tool.compress.sevenz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.io.file.BaseArchiveDirFileFun;

public class SevenZArchiveDirFileFun extends BaseArchiveDirFileFun {
	
	private static final String[] FORMAT_LIST = { "7z", "7Z" };
	
	public static OutputStream newWrapperStream( SevenZOutputFile archiveFile ) {
		return new SevenZOutputStream( archiveFile ); 
	}
	
	private SevenZOutputStream stream;
	
	public SevenZArchiveDirFileFun() {
		super();
	}
	
	public SevenZArchiveDirFileFun( File rootDir, File output ) throws IOException {
		this.init( rootDir, output);
	}
	
	@Override
	public void init(File rootDir, File output) throws IOException {
		super.init(rootDir, output);
		this.stream = new SevenZOutputStream( new SevenZOutputFile( output ) );
	}

	@Override
	public void handleFile( File file ) throws IOException {
		String entryName = this.getEntryName( file );
    	SevenZArchiveEntry entry = this.stream.getArchiveFile().createArchiveEntry( file , entryName);
    	this.stream.getArchiveFile().putArchiveEntry( entry );
    	FileInputStream in = new FileInputStream( file );
    	StreamIO.pipeStream( in , this.stream, StreamIO.MODE_CLOSE_IN_ONLY );
    	this.stream.getArchiveFile().closeArchiveEntry();
	}

	@Override
	public String[] getFormatList() {
		return FORMAT_LIST;
	}

	@Override
	public void close() throws IOException {
		this.stream.close();
	}
	
}

