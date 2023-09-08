package org.fugerit.java.core.jvfs.cl;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.fugerit.java.core.io.helper.HelperIOException;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;

public class UriFileClJMount {
	
	private UriFileClJMount() {}
	
	public static File toFile( String packageName ) throws IOException {
		return HelperIOException.get(() -> {
			URI uri = Thread.currentThread().getContextClassLoader().getResource( packageName ).toURI();
			return new File( uri );
		});
	}
		
    public static JVFS createJVFS( String packageName ) throws IOException {
    	return HelperIOException.get(() -> {
    		URI uri = Thread.currentThread().getContextClassLoader().getResource( packageName ).toURI();
			File root = new File( uri );
			return RealJMount.createJVFS( root );
    	});
    }

}
