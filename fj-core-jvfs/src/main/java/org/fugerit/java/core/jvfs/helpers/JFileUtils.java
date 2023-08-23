package org.fugerit.java.core.jvfs.helpers;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.lang.helpers.Wrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JFileUtils {
	
	private JFileUtils() {}
	
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
	
	public static String createPath( String parentPath, String name ) {
		String path = StringUtils.valueWithDefault( parentPath, "" );
		if ( path.endsWith( JFile.SEPARATOR ) ) {
			path+=name;
		} else {
			path+=JFile.SEPARATOR+name;
		}
		return path;
	}
	
	public static PathDescriptor pathDescriptor( String path ) {
		String parentPath = "";
		String name = "";
		String normalizedPath = normalizePath( path );
		int index = normalizedPath.lastIndexOf( JFile.SEPARATOR );
		log.debug( "path:'{}', normalized path:'{}'", path, normalizedPath );
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
	
	@SuppressWarnings("unchecked")
	public static JFile unwrapJFile( JFile jFile ) {
		while ( jFile instanceof Wrapper ) {
			jFile = ((Wrapper<JFile>)jFile).unwrap();
		}
		return jFile;
	}
	
}
