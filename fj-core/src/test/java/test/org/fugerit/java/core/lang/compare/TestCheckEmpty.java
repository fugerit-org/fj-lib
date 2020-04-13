package test.org.fugerit.java.core.lang.compare;

import static org.junit.Assert.fail;

import org.fugerit.java.core.lang.compare.CheckEmptyHelper;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestCheckEmpty extends BasicTest {
	
	public static boolean EMPTY = true;
	
	public static boolean NOT_EMPTY = false;
	
	private void checkEmptyHelper( Object value, boolean shouldBeEmpty ) {
		logger.error( "Test : {} -> {}", value, shouldBeEmpty );
		logger.debug( "Test : {} -> {}", value, shouldBeEmpty );
		boolean empty = CheckEmptyHelper.isEmpty( value );
		if ( empty && !shouldBeEmpty ) {
			fail( "Value is empty is not expected" );
		} else if ( !empty && shouldBeEmpty ) {
			fail( "Value is not empty is expected" );
		}
	}
	
	@Test
	public void test001() {
		this.checkEmptyHelper( "test" , NOT_EMPTY );
	}

	@Test
	public void test002() {
		this.checkEmptyHelper( "" , EMPTY );
	}
	
	@Test
	public void test003() {
		this.checkEmptyHelper( null , EMPTY );
	}
	
	@Test
	public void test004() {
		this.checkEmptyHelper( new CheckEmptyModel( "", "" ) , EMPTY );
	}
	
	@Test
	public void test005() {
		this.checkEmptyHelper( new CheckEmptyModel( "1", "2" ) , NOT_EMPTY );
	}
	
	@Test
	public void test006() {
		this.checkEmptyHelper( new CheckEmptyModel( "1", null ) , NOT_EMPTY );
	}
	
	@Test
	public void test007() {
		this.checkEmptyHelper( new CheckEmptyModel( null, "2" ) , NOT_EMPTY );
	}
	
	@Test
	public void test008() {
		this.checkEmptyHelper( new CheckEmptyModel( null, null ) , EMPTY );
	}
	
}
