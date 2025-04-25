package test.org.fugerit.java.core.lang.compare;

import org.fugerit.java.core.lang.compare.CompareHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCompareHelper {

	@Test
	public void testCompareEquals1() {
		boolean ok = CompareHelper.equals( String.valueOf( "test1" ) , String.valueOf( "test1" ) );
		Assertions.assertTrue(ok);
	}
	
	@Test
	public void testCompareEquals2() {
		boolean ok = CompareHelper.equals( String.valueOf( "test4" ), null );
		Assertions.assertFalse(ok);
	}

	@Test
	public void testCompareEquals3() {
		boolean ok = CompareHelper.equals( null, String.valueOf( "test5" ) );
		Assertions.assertFalse(ok);
	}
	
	@Test
	public void testCompareEquals4() {
		boolean ok = CompareHelper.equals( null, null );
		Assertions.assertTrue(ok);
	}
	
	@Test
	public void testCompareNotEquals() {
		boolean ok = CompareHelper.notEquals( String.valueOf( "test2" ), String.valueOf( "test3" ) );
		Assertions.assertTrue(ok);
	}	
	
}
