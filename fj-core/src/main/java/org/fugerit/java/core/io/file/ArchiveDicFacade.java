package org.fugerit.java.core.io.file;

import java.io.File;
import java.util.Properties;

import org.fugerit.java.core.io.FileIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;

public class ArchiveDicFacade {

	private Properties mapHandlers = new Properties();
	
	private static ArchiveDicFacade instance;
	
	static {
		instance = new ArchiveDicFacade();
		instance.register( new ZipArchiveDirFileFun() );
	}
	
	public void register( BaseArchiveDirFileFun handler ) {
		for ( int k=0; k<handler.getFormatList().length; k++ ) {
				String extension = handler.getFormatList()[k];
				mapHandlers.setProperty( extension , handler.getClass().getName() ); 
		}
	}
	
	public BaseArchiveDirFileFun newHandlerFromExtension( File rootDir, File output ) throws Exception {
		BaseArchiveDirFileFun handler = null;
		String fileName = output.getName();
		String extension = fileName.substring( fileName.lastIndexOf( '.' )+1 );
		String type = mapHandlers.getProperty( extension );
		if ( type == null  ) {
			throw new Exception( "Cannot handle file type "+extension+" , "+output.getCanonicalPath() );
		} else {
			handler = (BaseArchiveDirFileFun)ClassHelper.newInstance( type );
			handler.init(rootDir, output);
		}
		return handler;
	}
	
	public void compressByExtension( File rootDir, File output, boolean deleteSourceOnExit ) throws Exception {
		compressByExtension(rootDir, output);
		if ( deleteSourceOnExit ) {
			FileIO.recurseDir( rootDir , DeleteRecurseFileFun.INSTANCE, true );
		}
	}
	
	public void compressByExtension( File rootDir, File output ) throws Exception {
		BaseArchiveDirFileFun handler = newHandlerFromExtension(rootDir, output);
		FileIO.recurseDirClose( rootDir , handler );
	}

	public static ArchiveDicFacade getInstance() {
		return instance;
	}
	
}
