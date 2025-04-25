package test.org.fugerit.java.core.util.filterchain;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.filterchain.MiniFilterChain;
import org.fugerit.java.core.util.filterchain.MiniFilterConfig;
import org.fugerit.java.core.util.filterchain.MiniFilterContext;
import org.fugerit.java.core.util.filterchain.MiniFilterData;
import org.fugerit.java.core.util.filterchain.MiniFilterDebug;
import org.fugerit.java.core.util.filterchain.MiniFilterMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.org.fugerit.java.BasicTest;

public class TestMiniFilter extends BasicTest {

	private static final Logger logger = LoggerFactory.getLogger( TestMiniFilter.class );
	
	private static final String CONF_PATH = "core/util/filterchain/minifilter-test-config.xml";
	
	private static final String CONF_PATH_DUPLICATE = "core/util/filterchain/minifilter-test-duplicate-fail.xml";
	
	private static MiniFilterConfig CONFIG = MiniFilterConfig.initFromClassLoaderWithRuntimeException( CONF_PATH );
	
	public void testWorker( String chainId ) {
		this.testWorker(chainId, false);
	}
	
	public void testWorker( String chainId, boolean okOnException ) {
		this.testWorker(CONFIG, chainId, okOnException);
	}
	
	public void testWorker( MiniFilterConfig config, String chainId, boolean okOnException ) {
		boolean success = false;
		String message = "OK";
		try {
			logger.info(  "TEST START >>> "+chainId );
			MiniFilterChain chain = config.getChainCache( chainId );
			MiniFilterContext context = new MiniFilterContext();
			MiniFilterData data = new MiniFilterData() {};
			int res = chain.apply( context , data );
			logger.info(  "TEST END >>> "+chainId+" >> res:"+res+" [custom-config:"+context.getCustomConfig()+"]" );
			success = !okOnException;
		} catch (Exception e) {
			logger.info(  e.toString() );
			message = e.getMessage();
			success = okOnException;
		}
		if ( !success ) {
			fail( "Error : "+message );
		} else {
			logger.info(  "Test was success "+chainId );
		}
	}
	
	@Test
	void testChainBaseContinue() {
		this.testWorker( "chain-base-continue" );
	}
	
	@Test
	void testChainBaseSkip() {
		this.testWorker( "chain-base-skip" );
	}
	
	@Test
	void testChainBaseAll() {
		this.testWorker( "chain-base-all" );
	}
	
	@Test
	void testChainBaseNotFound() {
		this.testWorker( "chain-base-notfound", true );
	}

	@Test
	void testChainBaseLoadSafe00() {
		this.testWorker( "chain-base-loadsafe-00" );
	}
	
	@Test
	void testChainBaseLoadSafe01() {
		this.testWorker( "chain-base-loadsafe-01" );
	}
	
	@Test
	void testChainModule01() {
		this.testWorker( "chain-module01-test-01" );
	}	
	
	@Test
	void testChainModule02() {
		this.testWorker( "chain-module02-test-01" );
	}	
	
	@Test
	void testSerialization() {
		try {
			MiniFilterConfig deserializedValue = (MiniFilterConfig) this.fullSerializationTest(CONFIG);
			this.testWorker( deserializedValue, "chain-module01-test-01", false );
		} catch (IOException e) {
			this.failEx(e);
		}
	}	
	
	@Test
	void testLoadMap() {
		runTestEx( () -> {
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader(CONF_PATH) ) {
				MiniFilterConfig config = new MiniFilterConfig();
				MiniFilterMap map = MiniFilterConfig.loadConfigMap( is , config );
				Assertions.assertNotNull( map );
			}	
		} );
	}	
	
	@Test
	void testDebug() {
		runTestEx( () -> {
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader(CONF_PATH);
					StringWriter buffer = new StringWriter();
					PrintWriter writer = new PrintWriter(buffer)) {
				MiniFilterDebug.dumpConfig( writer , is );
				String debugText = buffer.toString();
				Assertions.assertNotEquals( 0 , debugText.length() );
				logger.info( "debug {}", debugText );
			}	
		} );
	}	
	
	@Test
	void testPrintConfig() {
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
		Iterator<String> it = CONFIG.getIdSet().iterator();
		int count = 0;
		while ( it.hasNext() ) {
			logger.info(  "id '"+it.next()+"'" );	
			count++;
		}
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
		assertTrue( count>0 );
	}		
	
	@Test
	void testDuplicate() {
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
		logger.info(  "*          TEST DUPLICATE                    *" );
		boolean ok = false;
		try {
			MiniFilterMap config = MiniFilterConfig.initFromClassLoaderWithRuntimeException( CONF_PATH_DUPLICATE );
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
