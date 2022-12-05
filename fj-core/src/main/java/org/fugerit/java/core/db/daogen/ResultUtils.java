package org.fugerit.java.core.db.daogen;

public class ResultUtils {

	public static <T> T oneOut( BasicDaoResult<T> result ) {
		return result.getSingleResult();
	}
	
}
