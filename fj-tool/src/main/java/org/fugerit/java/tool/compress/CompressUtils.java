package org.fugerit.java.tool.compress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.fugerit.java.core.io.helper.HelperIOException;

public class CompressUtils {

	private CompressUtils() {}
	
	public static void compress7Zipfile( File sourceDir, File  outputFile) throws IOException, FileNotFoundException {
		compressZipfile(sourceDir, outputFile);
	}
	
	
	public static void compressZipfile( File sourceDir, File  outputFile) throws IOException, FileNotFoundException {
		HelperIOException.apply( () -> ArchiveDicFacadeTool.getInstanceExt().compressByExtension(sourceDir, outputFile) );
	}
	
}
