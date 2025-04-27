package test.org.fugerit.java.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.fugerit.java.core.util.IteratorHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestIteratorHelper {

	@Test
	void testSafeIterator() {
		List<String> list = new ArrayList<String>( Arrays.asList( "1", "2", "3" ) );	// creates test list
		Iterator<String> it = list.iterator();
		// test iterator 1
		Iterator<String> myIt = IteratorHelper.createSimpleIteratorSafe(it::hasNext, it::next);
		while ( myIt.hasNext() ) {
			String current = myIt.next();
			log.info( "current : {}", current );
		}
		Assertions.assertThrows( NoSuchElementException.class , myIt::next );
		// test iterator 2
		Iterator<String> it2 = list.iterator();
		Iterator<String> myIt2 = IteratorHelper.createSimpleIterator(it2::hasNext, it2::next);
		if ( myIt2.hasNext() ) {
			String current = myIt2.next();
			log.info( "current : {}", current );
			Assertions.assertThrows( UnsupportedOperationException.class , myIt2::remove );
		}
		// test iterator 3
		Iterator<String> it3 = list.iterator();
		Iterator<String> myIt3 = IteratorHelper.createSimpleIterator(it3::hasNext, it3::next, () -> log.info( "no action on remove" ) );
		if ( myIt3.hasNext() ) {
			String current = myIt3.next();
			log.info( "current : {}", current );
			myIt3.remove();
			
		}
		// test iterator 4
		Iterator<String> it4 = list.iterator();
		Iterator<String> myIt4 = IteratorHelper.createSimpleIteratorSafe(it4::hasNext, it4::next, () -> log.info( "no action on remove" ));
		while ( myIt4.hasNext() ) {
			String current = myIt4.next();
			log.info( "current : {}", current );
			myIt4.remove();
		}
	}
	
}
