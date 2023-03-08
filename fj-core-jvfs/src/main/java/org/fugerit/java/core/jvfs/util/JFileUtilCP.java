package org.fugerit.java.core.jvfs.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Predicate;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.util.ObjectUtils;
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

	public static int copyFile(JFile from, JFile to, boolean recurse, boolean force, boolean verbose) throws IOException {
		return copyFile( from, to, recurse, force, verbose, ( f ) -> true );
	}

	public static int copyFile(JFile from, JFile to, boolean recurse, boolean force, boolean verbose, Predicate<JFile> filter) throws IOException {
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
						if ( filter.test( kidFrom ) ) {
							res+= copyFile(kidFrom, to.getChild( kidFrom.getName() ), recurse, force, verbose, filter);
						}
					}
				} else {
					throw new IOException( "Directories can only be copied recursively, from:("+from+") to:("+to+")" );
				}
			} else {
				// We want to create a blank file if the from input stream is null
				StreamIO.pipeStream(
						ObjectUtils.objectWithDefault( from.getInputStream() , new ByteArrayInputStream( "".getBytes() ) ) ,
						to.getOutputStream(),
						StreamIO.MODE_CLOSE_BOTH );
			}
		}
		return res;
	}
	
}
