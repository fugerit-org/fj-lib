package test.org.fugerit.java.core.lang.compare;

import static org.junit.jupiter.api.Assertions.fail;

import org.fugerit.java.core.lang.compare.CheckEmptyHelper;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;

class TestCheckEmpty extends BasicTest {
	
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
	void test001() {
		this.checkEmptyHelper( "test" , NOT_EMPTY );
	}

	@Test
	void test002() {
		this.checkEmptyHelper( "" , EMPTY );
	}
	
	@Test
	void test003() {
		this.checkEmptyHelper( null , EMPTY );
	}
	
	@Test
	void test004() {
		this.checkEmptyHelper( new CheckEmptyModel( "", "" ) , EMPTY );
	}
	
	@Test
	void test005() {
		this.checkEmptyHelper( new CheckEmptyModel( "1", "2" ) , NOT_EMPTY );
	}
	
	@Test
	void test006() {
		this.checkEmptyHelper( new CheckEmptyModel( "1", null ) , NOT_EMPTY );
	}
	
	@Test
	void test007() {
		this.checkEmptyHelper( new CheckEmptyModel( null, "2" ) , NOT_EMPTY );
	}
	
	@Test
	void test008() {
		this.checkEmptyHelper( new CheckEmptyModel( null, null ) , EMPTY );
	}
	
}
