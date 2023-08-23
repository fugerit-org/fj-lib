package org.fugerit.java.tool.compress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CompressUtils {

	private CompressUtils() {}
	
	public static void compress7Zipfile( File sourceDir, File  outputFile) throws IOException, FileNotFoundException {
		compressZipfile(sourceDir, outputFile);
	}
	
	
	public static void compressZipfile( File sourceDir, File  outputFile) throws IOException, FileNotFoundException {
		try {
			ArchiveDicFacadeTool.getInstanceExt().compressByExtension(sourceDir, outputFile);	
		} catch (Exception e) {
			throw new IOException( e );
		}
	}
	
	
}
