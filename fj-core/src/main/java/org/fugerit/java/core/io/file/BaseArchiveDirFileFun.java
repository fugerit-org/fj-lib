package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.IOException;

public abstract class BaseArchiveDirFileFun extends AbstractFileFun {

	private File output;
	
	private File rootDir;
	
	protected String rootFullPath;
	
	public abstract String[] getFormatList();
	
	protected BaseArchiveDirFileFun() {
		super();
	}

	public void init( File rootDir, File output ) throws IOException {
		this.output = output;
		this.rootDir = rootDir;
		this.rootFullPath = this.rootDir.getCanonicalPath();
	}
	
	public File getOutput() {
		return output;
	}

	public File getRootDir() {
		return rootDir;
	}	
	
	public String getEntryName( File file ) throws IOException {
		String entryPath = file.getCanonicalPath();
		if ( entryPath.startsWith( this.rootFullPath ) ) {
			entryPath = entryPath.substring( this.rootFullPath.length()+1 );
		}
		return entryPath;
	}
	
}
