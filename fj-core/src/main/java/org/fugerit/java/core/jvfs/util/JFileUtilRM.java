package org.fugerit.java.core.jvfs.util;

import java.io.IOException;

import org.fugerit.java.core.jvfs.JFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JFileUtilRM {
	
	private static final Logger logger = LoggerFactory.getLogger( JFileUtilRM.class );
	
	public static int deleteRecurse( JFile file ) throws IOException {
		return delete(file, true);
	}

	public static int deleteRecurseForce( JFile file ) throws IOException {
		return delete(file, true, true);
	}

	public static int deleteRecurseForceVerbose( JFile file ) throws IOException {
		return delete(file, true, true, false);
	}
	
	public static int delete( JFile file ) throws IOException {
		return delete(file, false);
	}
	
	public static int delete( JFile file, boolean recurse ) throws IOException {
		return delete(file, recurse, false);
	}

	public static int delete( JFile file, boolean recurse, boolean force ) throws IOException {
		return delete(file, recurse, force, false);
	}
	
	public static int delete( JFile file, boolean recurse, boolean force, boolean verbose ) throws IOException {
		int delete = 0;
		if ( recurse && file.isDirectory() ) {
			for ( JFile kid : file.lsFiles() ) {
				delete+= delete(kid, recurse, force, verbose);
			}
		}
		boolean res = file.delete();
		if ( verbose ) {
			logger.info( "delete file:{} result:{}", file, res );
		}
		if ( force && file.exists() && !res ) {
			throw new IOException( "Failed to delete file : "+file );
		}
		return delete;
	}
	
}
