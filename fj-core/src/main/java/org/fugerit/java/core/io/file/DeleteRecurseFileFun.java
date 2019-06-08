package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.IOException;

public class DeleteRecurseFileFun extends AbstractFileFun {

	/**
	 * 
	 * Thread safe instance
	 */
	public static final DeleteRecurseFileFun INSTANCE = new DeleteRecurseFileFun();
	
	@Override
	public void handleFile(File file) throws IOException {
		file.delete();
	}
	
}
