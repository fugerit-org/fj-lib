package test.org.fugerit.java.core.util.tree;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTreeConfigKeyStringXML {

	private final static Logger logger = LoggerFactory.getLogger( TestTreeConfigKeyStringXML.class );
	
	private static final String CONF_PATH = "core/util/tree/tree-test.xml";
	
	private void findWorker( TestTreeConfig config, String key ) {
		TestNode node = config.findNodeByKey( key );
		logger.info( "get by key "+key+" : "+node );
		if ( node == null ) {
			fail( "Node not fould for key "+key );
		}
	}
	
	@Test
	public void testConfig() throws Exception {
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
		logger.info(  "*          TEST CONFIG                       *" );
		try {
			TestTreeConfig config = TestTreeConfig.load( CONF_PATH );	
			this.findWorker( config, "1" );
			this.findWorker( config, "3" );
		} catch (Exception e) {
			logger.error( "Config error : "+e, e );
			throw e;
		}
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
	}	
	
}
