package test.org.fugerit.java.core.lang.compare;

import org.fugerit.java.core.lang.compare.CompareHelper;
import org.junit.Assert;
import org.junit.Test;

public class TestCompareHelper {

	@Test
	public void testCompareEquals1() {
		boolean ok = CompareHelper.equals( String.valueOf( "test1" ) , String.valueOf( "test1" ) );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testCompareEquals2() {
		boolean ok = CompareHelper.equals( String.valueOf( "test4" ), null );
		Assert.assertFalse(ok);
	}

	@Test
	public void testCompareEquals3() {
		boolean ok = CompareHelper.equals( null, String.valueOf( "test5" ) );
		Assert.assertFalse(ok);
	}
	
	@Test
	public void testCompareEquals4() {
		boolean ok = CompareHelper.equals( null, null );
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testCompareNotEquals() {
		boolean ok = CompareHelper.notEquals( String.valueOf( "test2" ), String.valueOf( "test3" ) );
		Assert.assertTrue(ok);
	}	
	
}
