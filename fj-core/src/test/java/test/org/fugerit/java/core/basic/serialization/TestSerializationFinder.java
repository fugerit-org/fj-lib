package test.org.fugerit.java.core.basic.serialization;

import org.fugerit.java.core.db.daogen.GenericIdFinder;
import org.fugerit.java.core.db.helpers.DAOID;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import test.org.fugerit.java.BasicTest;

public class TestSerializationFinder extends BasicTest {

	@Test
	public void testOkSerialization() {
		try {
			long value = 1979L;
			GenericIdFinder<DAOID> finder = new GenericIdFinder<>();
			finder.setId( DAOID.valueOf( value ) );
			logger.info( "starting value {}", finder );
			@SuppressWarnings("unchecked")
			GenericIdFinder<DAOID> deserializaedValue = (GenericIdFinder<DAOID>) this.fullSerializationTest( finder );
			Assertions.assertEquals( value , deserializaedValue.getId().longValue() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testFailSerialization() {
		boolean ok = false;
		try {
			long value = 1979L;
			GenericIdFinder<NotSerializableID> finder = new GenericIdFinder<>();
			finder.setId( NotSerializableID.valueOf( value ) );
			logger.info( "starting value {}", finder );
			@SuppressWarnings("unchecked")
			GenericIdFinder<NotSerializableID> deserializaedValue = (GenericIdFinder<NotSerializableID>) this.fullSerializationTest( finder );
			logger.info( "test failed! deserializaedValue {}", deserializaedValue ); // this line is not expected to be reached
		} catch (java.io.NotSerializableException nse) {
			logger.info( "Test ok : {}", nse.toString() );
			ok = true; // this is the purpose of the test
		} catch (Exception e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
}