package test.org.fugerit.java.core.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Properties;

import org.fugerit.java.core.util.PropsIO;
import org.junit.jupiter.api.Test;

public class TesterPropsIO {

	public static final String PROP_TEST_KEY = "TestPropKey1";
	
	@Test
	void testReadFromClassLoader() {
		try {
			Properties props = PropsIO.loadFromClassLoader( "core/util/test-props.io.properties" );
			String testValue = props.getProperty( PROP_TEST_KEY );
			System.out.println( testValue );
			if ( testValue == null ) {
				fail( "Prop not found "+PROP_TEST_KEY );
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Error : "+e.getMessage() );	
		}
	}

}
