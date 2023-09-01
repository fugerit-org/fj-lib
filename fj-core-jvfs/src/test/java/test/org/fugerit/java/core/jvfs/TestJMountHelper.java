package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.cl.UriFileClJMount;
import org.fugerit.java.core.jvfs.file.RealJMount;

public class TestJMountHelper {

	public static JVFS getSafeRealJVFS( String basePath ) {
		JVFS jvfs = null;
		try {
			File baseFolder = new File( basePath );	
			if ( !baseFolder.exists() ) {
				baseFolder.mkdirs();
			}
			jvfs = RealJMount.createJVFS( baseFolder );
		} catch (Exception e) {
			throw ConfigRuntimeException.convertExMethod( "getSafeRealJVFS("+basePath+")", e ); 
		}
		
		return jvfs;
	}
	
	public static JVFS getRealJVFSDefault() throws IOException {
		return RealJMount.createJVFS( new File( "src/test/resources/core-jvfs" ) );
	}

	public static JVFS getClassLoaderJVFSDefault() throws IOException {
		return UriFileClJMount.createJVFS( "core-jvfs" );
	}
	
}
