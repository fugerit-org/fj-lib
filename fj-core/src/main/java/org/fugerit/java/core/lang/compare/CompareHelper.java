package org.fugerit.java.core.lang.compare;

public class CompareHelper {

	private CompareHelper() {}
	
	public static boolean equals( Object o1, Object o2 ) {
		boolean eq = true;
		if ( o1 == null ) {
			eq = ( o2 == null );
		} else {
			eq = o1.equals( o2 );  
		}
		return eq;
	}
	
	public static boolean notEquals( Object o1, Object o2 ) {
		return !equals(o1, o2);
	}
	
}
