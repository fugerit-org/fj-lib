package org.fugerit.java.tool.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.utils.IOUtils;
import org.fugerit.java.core.io.StreamIO;

public class CompressUtils {

	public static void compress7Zipfile( File sourceDir, File  outputFile) throws IOException, FileNotFoundException {
		SevenZOutputFile zipFile = new SevenZOutputFile( outputFile );
	    compressDirectoryTo7Zipfile(sourceDir, sourceDir, zipFile);
	    IOUtils.closeQuietly(zipFile);
	}
	
	private static void compressDirectoryTo7Zipfile(File rootDir, File sourceDir, SevenZOutputFile out) throws IOException, FileNotFoundException {
	    for (File file : sourceDir.listFiles()) {
	        if (file.isDirectory()) {
	        	compressDirectoryTo7Zipfile(rootDir, new File( sourceDir , file.getName() ), out);
	        } else {
	        	String entryName = sourceDir.getCanonicalPath().replace(rootDir.getCanonicalPath(), "") + file.getName();
	        	SevenZArchiveEntry entry = out.createArchiveEntry( file , entryName);
	        	out.putArchiveEntry( entry );
	        	FileInputStream in = new FileInputStream( file );
	        	byte[] buffer = new byte[1024];
	        	int read = in.read( buffer ); 
	        	while ( read > 0 ) {
	        		out.write( buffer, 0, read );
	        		read = in.read( buffer );
	        	}
	        	in.close();
	        	out.closeArchiveEntry();
	        }
	    }
	}
	
	public static void compressZipfile( File sourceDir, File  outputFile) throws IOException, FileNotFoundException {
	    ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(outputFile));
	    compressDirectoryToZipfile(sourceDir, sourceDir, zipFile);
	    IOUtils.closeQuietly(zipFile);
	}
	
	private static void compressDirectoryToZipfile(File rootDir, File sourceDir, ZipOutputStream out) throws IOException, FileNotFoundException {
	    for (File file : sourceDir.listFiles()) {
	        if (file.isDirectory()) {
	            compressDirectoryToZipfile(rootDir, new File( sourceDir , file.getName() ), out);
	        } else {
	            ZipEntry entry = new ZipEntry(sourceDir.getCanonicalPath().replace(rootDir.getCanonicalPath(), "") + file.getName());
	            out.putNextEntry(entry);
	            FileInputStream in = new FileInputStream( file );
	            StreamIO.pipeStream( in , out, StreamIO.MODE_CLOSE_IN_ONLY );
	        }
	    }
	}
	
}
