package org.fugerit.java.core.util.result;

import java.util.ArrayList;
import java.util.List;

public class ResultHelper {

	public static <T> List<T> createList( T obj ) {
		List<T> list = new ArrayList<>();
		if ( obj != null ) {
			list.add( obj );	
		}
		return list;
	}
	
}
