package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.fugerit.java.core.io.StreamIO;

public class ZipArchiveDirFileFun extends BaseArchiveDirFileFun {
	
	public static final String[] FORMAT_LIST = { "zip", "ZIP" };
	
	private ZipOutputStream stream;
	
	public ZipArchiveDirFileFun() {
		super();
	}
	
	public ZipArchiveDirFileFun( File rootDir, File output ) throws IOException {
		this.init( rootDir, output);
	}
	
	@Override
	public void init(File rootDir, File output) throws IOException {
		super.init(rootDir, output);
		this.stream = new ZipOutputStream( new FileOutputStream( output ) );
	}

	@Override
	public void handleFile( File file ) throws IOException {
		String entryName = this.getEntryName( file );
    	ZipEntry entry = new ZipEntry( entryName );
    	this.stream.putNextEntry( entry );
    	FileInputStream in = new FileInputStream( file );
    	StreamIO.pipeStream( in , this.stream, StreamIO.MODE_CLOSE_IN_ONLY );
    	this.stream.closeEntry();
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

