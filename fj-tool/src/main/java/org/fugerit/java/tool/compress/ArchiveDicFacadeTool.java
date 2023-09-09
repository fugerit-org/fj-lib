package org.fugerit.java.tool.compress;

import org.fugerit.java.core.io.file.ArchiveDicFacade;
import org.fugerit.java.tool.compress.sevenz.SevenZArchiveDirFileFun;

public class ArchiveDicFacadeTool extends ArchiveDicFacade {

	private ArchiveDicFacadeTool() {}
	
	private static ArchiveDicFacade instanceExt;
	
	static {
		instanceExt = getInstance();
		instanceExt.register( new SevenZArchiveDirFileFun() );
	}
	
	public static ArchiveDicFacade getInstanceExt() {
		return instanceExt;
	}
	
}
