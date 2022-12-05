package test.org.fugerit.java.core.util;

import static org.junit.Assert.*;

import java.util.Properties;

import org.fugerit.java.core.util.PropsIO;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TesterPropsIOSubProps {

	public static final String PROP_TEST_KEY = "TestPropKey1";
	
	private static final Logger logger = LoggerFactory.getLogger( TesterPropsIOSubProps.class );
	
	private static final long EXPECTED_SUB_PROPS_SIZE = 2;
	
	@Test
	public void testReadFromClassLoader() {
		try {
			Properties props = PropsIO.loadFromClassLoader( "core/util/test-props-io-prefix.properties" );
			logger.info( "full props -> {}", props );
			Properties subProps = PropsIO.subProps( props , "prefix1", "-", true );
			logger.info( "sub  props -> {}", subProps );
			Assert.assertEquals( "Correct sub props size", EXPECTED_SUB_PROPS_SIZE, subProps.size() );
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Error : "+e.getMessage() );	
		}
	}

}
