package org.fugerit.java.core.jvfs.helpers;

import org.fugerit.java.core.jvfs.JFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JFileUtils {

	private static final Logger logger = LoggerFactory.getLogger( JFileUtils.class );
	
	public static boolean isRoot( String path ) {
		return JFile.SEPARATOR.equals( path );
	}
	
	public static boolean isDirectory( String path ) {
		return path.endsWith( JFile.SEPARATOR );
	}
	
	public static String normalizePath( String path ) {
		String res = path;
		if ( isDirectory( path ) ) {
			res = path.substring( 0, path.length()-JFile.SEPARATOR.length() );
		}
		return res;
	}
	
	public static PathDescriptor pathDescriptor( String path ) {
		String parentPath = null;
		String name = null;
		String normalizedPath = normalizePath( path );
		int index = normalizedPath.lastIndexOf( JFile.SEPARATOR );
		logger.debug( "path:'{}', normalized path:'{}'", path, normalizedPath );
		if ( index >= 0 ) {
			int sepIndex = index+JFile.SEPARATOR.length();
			name = normalizedPath.substring( sepIndex );
			parentPath = normalizedPath.substring( 0, sepIndex );
			if ( isDirectory( path ) ) {
				name+= JFile.SEPARATOR;
			}
		} else {
			name = path;
		}
		return new PathDescriptor(parentPath, name);
	}
	
}
