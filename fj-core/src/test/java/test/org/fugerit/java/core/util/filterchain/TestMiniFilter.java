package test.org.fugerit.java.core.util.filterchain;

import static org.junit.Assert.fail;

import java.util.Iterator;

import org.fugerit.java.core.util.filterchain.MiniFilterChain;
import org.fugerit.java.core.util.filterchain.MiniFilterConfig;
import org.fugerit.java.core.util.filterchain.MiniFilterContext;
import org.fugerit.java.core.util.filterchain.MiniFilterData;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMiniFilter {

	private final static Logger logger = LoggerFactory.getLogger( TestMiniFilter.class );
	
	private static final String CONF_PATH = "core/util/filterchain/minifilter-test-config.xml";
	
	private static final String CONF_PATH_DUPLICATE = "core/util/filterchain/minifilter-test-duplicate-fail.xml";
	
	private static MiniFilterConfig CONFIG = MiniFilterConfig.initFromClassLoaderWithRuntimeException( CONF_PATH );
	
	public void testWorker( String chainId ) {
		this.testWorker(chainId, false);
	}
	
	public void testWorker( String chainId, boolean okOnException ) {
		boolean success = false;
		String message = "OK";
		try {
			logger.info(  "TEST START >>> "+chainId );
			MiniFilterChain chain = CONFIG.getChainCache( chainId );
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
	public void testChainBaseContinue() {
		this.testWorker( "chain-base-continue" );
	}
	
	@Test
	public void testChainBaseSkip() {
		this.testWorker( "chain-base-skip" );
	}
	
	@Test
	public void testChainBaseAll() {
		this.testWorker( "chain-base-all" );
	}
	
	@Test
	public void testChainBaseNotFound() {
		this.testWorker( "chain-base-notfound", true );
	}

	@Test
	public void testChainBaseLoadSafe00() {
		this.testWorker( "chain-base-loadsafe-00" );
	}
	
	@Test
	public void testChainBaseLoadSafe01() {
		this.testWorker( "chain-base-loadsafe-01" );
	}
	
	@Test
	public void testChainModule01() {
		this.testWorker( "chain-module01-test-01" );
	}	
	
	@Test
	public void testChainModule02() {
		this.testWorker( "chain-module02-test-01" );
	}	
	
	@Test
	public void testPrintConfig() {
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
		Iterator<String> it = CONFIG.getIdSet().iterator();
		while ( it.hasNext() ) {
			logger.info(  "id '"+it.next()+"'" );	
		}
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
	}		
	
	@Test
	public void testDuplicate() {
		logger.info(  "**********************************************" );
		logger.info(  "**********************************************" );
		logger.info(  "*          TEST DUPLICATE                    *" );
		boolean ok = false;
		try {
			MiniFilterConfig config = MiniFilterConfig.initFromClassLoaderWithRuntimeException( CONF_PATH_DUPLICATE );
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
