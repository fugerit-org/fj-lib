package org.fugerit.java.core.javagen;

public class GeneratorNameHelper {

	private GeneratorNameHelper() {}
	
	public static String toPropertyName( String name ) {
		String res = toClassName( name );
		res = res.substring( 0, 1 ).toLowerCase()+res.substring( 1 );
		return res;
	}
	
	public static String toClassName( String name ) {
		String res = "";
		String part[] = name.toLowerCase().split( "_" );
		for ( int k=0; k<part.length; k++ ) {
			res+= part[k].substring( 0, 1 ).toUpperCase()+part[k].substring( 1 );
		}
		return res;
	}
	
	public static String classFromPackage( String name ) {
		int index = name.lastIndexOf( '.' );
		String res = name;
		if ( index >= 0 ) {
			res = name.substring( index+1 );
		}
		return res;
	}
	
}
