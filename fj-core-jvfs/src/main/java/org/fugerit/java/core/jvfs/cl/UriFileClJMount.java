package org.fugerit.java.core.jvfs.cl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;

public class UriFileClJMount implements JMount, Serializable {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 6365971783948169969L;

	transient private JMount mount;
	
	public static File toFile( String packageName ) throws IOException {
		File res = null;
		try {
        	URI uri = Thread.currentThread().getContextClassLoader().getResource( packageName ).toURI();
			res = new File( uri );
		} catch (Exception e) {
			throw new IOException( e );
		}
		return res;
	}
		
    public static JVFS createJVFS( String packageName ) throws IOException {
    	JVFS res = null;
        try {
        	URI uri = Thread.currentThread().getContextClassLoader().getResource( packageName ).toURI();
			File root = new File( uri );
			res = RealJMount.createJVFS( root );
		} catch (Exception e) {
			throw new IOException( e );
		}
        return res;
    }

	@Override
	public JFile getJFile(JVFS jvfs, String point, String relPath) {
		return this.mount.getJFile(jvfs, point, relPath);
	}
    
}
