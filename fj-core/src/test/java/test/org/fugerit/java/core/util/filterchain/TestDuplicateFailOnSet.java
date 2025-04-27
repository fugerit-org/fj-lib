package test.org.fugerit.java.core.util.filterchain;

import static org.junit.jupiter.api.Assertions.fail;

import org.fugerit.java.core.util.filterchain.MiniFilterConfig;
import org.fugerit.java.core.util.filterchain.MiniFilterMap;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TestDuplicateFailOnSet {

	private static final Logger logger = LoggerFactory.getLogger( TestDuplicateFailOnSet.class );
	
	private static final String CONF_PATH_DUPLICATE_FAIL_ON_SET = "core/util/filterchain/minifilter-test-duplicate-fail-on-set.xml";
	
	@Test
	void testDuplicate() {
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
		logger.info(  "*          TEST DUPLICATE                    *" );
		boolean ok = false;
		try {
			MiniFilterMap config = MiniFilterConfig.initFromClassLoaderWithRuntimeException( CONF_PATH_DUPLICATE_FAIL_ON_SET );
			logger.info( config.toString() );
		} catch (Exception e) {
			logger.info( e.getMessage() );
			if ( e.getMessage().toLowerCase().contains( "duplicate" ) ) {
				ok = true;
			}
		}
		if ( !ok ) {
			fail( "Test failed, duplicate exception non triggered" );
		}
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
	}	
	
}
