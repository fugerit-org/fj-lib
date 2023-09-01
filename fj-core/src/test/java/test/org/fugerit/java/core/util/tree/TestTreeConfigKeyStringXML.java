package test.org.fugerit.java.core.util.tree;

import static org.junit.Assert.fail;

import org.fugerit.java.core.cfg.ConfigException;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestTreeConfigKeyStringXML extends BasicTest {

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
	public void testConfig() {
		try {
			TestTreeConfig config = TestTreeConfig.load( CONF_PATH );	
			this.findWorker( config, "1" );
			this.findWorker( config, "3" );
			log.info( "tree -> {}", config.getTree() );
			Assert.assertNotNull( config.getTree() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}	
	
	@Test
	public void testConfigKo() throws Exception {
		try {
			TestTreeConfig config = TestTreeConfig.load( CONF_PATH_KO );	
			fail( "This code should not be reached : "+config );
		} catch (Exception e) {
			Assert.assertTrue( e instanceof ConfigException );
		}
	}	
	
	@Test
	public void testConfigMultipleRoot() throws Exception {
		try {
			TestTreeConfig config = TestTreeConfig.load( CONF_PATH_MULTIPLE_ROOT );
			fail( "This code should not be reached : "+config );
		} catch (Exception e) {
			Assert.assertTrue( e instanceof ConfigException );
		}
	}	
	
}
