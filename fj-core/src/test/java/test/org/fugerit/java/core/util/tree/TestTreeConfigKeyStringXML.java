package test.org.fugerit.java.core.util.tree;

import static org.junit.jupiter.api.Assertions.fail;

import org.fugerit.java.core.cfg.ConfigException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
class TestTreeConfigKeyStringXML extends BasicTest {

	private static final String CONF_PATH = "core/util/tree/tree-test.xml";
	
	private static final String CONF_PATH_KO = "core/util/tree/tree-test_ko.xml";
	
	private static final String CONF_PATH_MULTIPLE_ROOT = "core/util/tree/tree-test_multiple_root.xml";

	private void findWorker( TestTreeConfig config, String key ) {
		TestNode node = config.findNodeByKey( key );
		log.info( "get by key "+key+" : "+node );
		if ( node == null ) {
			fail( "Node not fould for key "+key );
		}
	}
	
	@Test
	void testConfig() {
		try {
			TestTreeConfig config = TestTreeConfig.load( CONF_PATH );	
			this.findWorker( config, "1" );
			this.findWorker( config, "3" );
			log.info( "tree -> {}", config.getTree() );
			Assertions.assertNotNull( config.getTree() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}	
	
	@Test
	void testConfigKo() throws Exception {
		try {
			TestTreeConfig config = TestTreeConfig.load( CONF_PATH_KO );	
			fail( "This code should not be reached : "+config );
		} catch (Exception e) {
			Assertions.assertTrue( e instanceof ConfigException );
		}
	}	
	
	@Test
	void testConfigMultipleRoot() throws Exception {
		try {
			TestTreeConfig config = TestTreeConfig.load( CONF_PATH_MULTIPLE_ROOT );
			fail( "This code should not be reached : "+config );
		} catch (Exception e) {
			Assertions.assertTrue( e instanceof ConfigException );
		}
	}	
	
}
