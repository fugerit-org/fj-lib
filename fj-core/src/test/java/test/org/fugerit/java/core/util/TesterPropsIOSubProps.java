package test.org.fugerit.java.core.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Properties;

import org.fugerit.java.core.util.PropsIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TesterPropsIOSubProps {

	public static final String PROP_TEST_KEY = "TestPropKey1";
	
	private static final Logger logger = LoggerFactory.getLogger( TesterPropsIOSubProps.class );
	
	private static final long EXPECTED_SUB_PROPS_SIZE = 2;
	
	@Test
	void testReadFromClassLoader() {
		try {
			Properties props = PropsIO.loadFromClassLoader( "core/util/test-props-io-prefix.properties" );
			logger.info( "full props -> {}", props );
			Properties subProps = PropsIO.subProps( props , "prefix1", "-", true );
			logger.info( "sub  props -> {}", subProps );
			Assertions.assertEquals( EXPECTED_SUB_PROPS_SIZE, subProps.size(), () -> "Correct sub props size" );
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Error : "+e.getMessage() );	
		}
	}

}
