package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteRecurseFileFun extends AbstractFileFun {

	/**
	 * 
	 * Thread safe instance
	 */
	public static final DeleteRecurseFileFun INSTANCE = new DeleteRecurseFileFun();
	
	@Override
	public void handleFile(File file) throws IOException {
		boolean res = Files.deleteIfExists( file.toPath() );
		if ( !res ) {
			log.warn( "false result on file.delete() : {}", file );
		}
	}
	
}
