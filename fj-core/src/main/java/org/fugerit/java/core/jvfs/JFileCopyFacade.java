package org.fugerit.java.core.jvfs;

import java.io.IOException;

import org.fugerit.java.core.io.StreamIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JFileCopyFacade {

	private final static Logger logger = LoggerFactory.getLogger( JFileCopyFacade.class );
	
	public static int copyFile( JFile from, JFile to ) throws IOException {
		return copyFile(from, to, false);
	}
	
	public static int copyFile( JFile from, JFile to, boolean recurse ) throws IOException {
		return copyFile(from, to, recurse, false);
	}
	
	public static int copyFile( JFile from, JFile to, boolean recurse, boolean force ) throws IOException {
		return copyFile(from, to, recurse, force, false);
	}
	
	public static int copyFileRecurse( JFile from, JFile to ) throws IOException {
		return copyFile(from, to, true );
	}
	
	public static int copyFileRecurseForce( JFile from, JFile to ) throws IOException {
		return copyFile(from, to, true, true );
	}
	
	public static int copyFileRecurseForceVerbose( JFile from, JFile to ) throws IOException {
		return copyFile(from, to, true, true, true );
	}
	
	public static int copyFile( JFile from, JFile to, boolean recurse, boolean force, boolean verbose ) throws IOException {
		int res = 1;
		if ( verbose ) {
			logger.info( "copyFile( recurse:"+recurse+", force:"+force+" ) {} -> {}", from, to );
		}
		if ( to.exists() && !force ) {
			throw new IOException( "You can overwrite existing files only with the 'force' flag" );
		} else {
			if ( from.isFile() && to.isFile() )  {
				StreamIO.pipeStream( from.getInputStream() , to.getOutputStream(), StreamIO.MODE_CLOSE_BOTH );
			} else  if ( from.isDirectory() && to.isDirectory() )  {
				if ( recurse ) {
					to.mkdir();
					JFile[] list = from.listFiles();
					for ( int k=0; k<list.length; k++ ) {
						JFile kidFrom = list[k];
						JFile kidTo = to.getChild( kidFrom.getName() );
						res+= copyFile( kidFrom, kidTo, recurse, force, verbose);
					}
				} else {
					throw new IOException( "Directories can only be copied recursively, from:("+from+") to:("+to+")" );
				}
			} else {
				if ( verbose ) {
					logger.warn( "from {} -> isDirectory:{}", from, from.isDirectory() );
					logger.warn( "to {} -> isDirectory:{}", to, to.isDirectory() );
				}
				throw new IOException( "You can only copy a two files or two directories, from:("+from+") to:("+to+")" );
			}
		}
		return res;
	}
	
}
