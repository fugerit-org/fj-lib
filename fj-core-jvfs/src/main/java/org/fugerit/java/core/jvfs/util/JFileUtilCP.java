package org.fugerit.java.core.jvfs.util;

import java.io.IOException;
import java.io.InputStream;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JFileUtilCP {

	private final static Logger logger = LoggerFactory.getLogger( JFileUtilCP.class );
	
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
			if ( from.isDirectory() && to.isDirectory() )  {
				if ( recurse ) {
					if ( !to.exists() ) {
						to.mkdir();	
					}
					for ( JFile kidFrom : from.lsFiles() ) {
						res+= copyFile(kidFrom, to.getChild( kidFrom.getName() ), recurse, force, verbose);
					}
				} else {
					throw new IOException( "Directories can only be copied recursively, from:("+from+") to:("+to+")" );
				}
			} else {
				InputStream fromIS = from.getInputStream();
				if ( fromIS != null ) {
					StreamIO.pipeStream( fromIS , to.getOutputStream(), StreamIO.MODE_CLOSE_BOTH );
				} else {
					if ( verbose ) {
						logger.info( "Input file empty (getInputStream() == null) : {}", from.describe() );
					}
				}
			}
		}
		return res;
	}
	
}
