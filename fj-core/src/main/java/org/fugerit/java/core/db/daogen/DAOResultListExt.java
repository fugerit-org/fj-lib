package org.fugerit.java.core.db.daogen;

import java.util.Optional;
import java.util.stream.Stream;

import org.fugerit.java.core.db.dao.DAORuntimeException;

/**
 * 
 * <p>Extensions for stream() and Optional in DAOResultList.</p>
 * 
 * @param <T>	the type of the result.
 */
public interface DAOResultListExt<T> extends DaoResultList<T> {

	/**
	 * <p>Return a stream of the list contained in this result.</p>
	 * 
	 * <p>Delegate for getList().stream().</p>
	 * 
	 * @return	a stream on the contained list.
	 */
	Stream<T> stream();
	
	/**
	 * <p>Return the first element if any, or an empty optional.</p>
	 * 
	 * @return	the first and only element
	 * @throws 			DAORuntimeException if more than one result is found.
	 */
	Optional<T> getOne();

	/**
	 * <p>Return the first element if any, or an empty optional.</p>
	 * 
	 * @return	the first (no exception is thrown if there is more than one element in the result)
	 */
	Optional<T> getFirst();
	
}
