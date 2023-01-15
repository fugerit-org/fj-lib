package test.org.fugerit.java;

import static org.junit.Assert.fail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicTest {

	protected final static Logger logger = LoggerFactory.getLogger( BasicTest.class );
	
	protected void failEx( Exception e ) {
		String message = "Error : "+e;
		logger.error( "Error "+e, e );
		fail( message );
	}
	
}
