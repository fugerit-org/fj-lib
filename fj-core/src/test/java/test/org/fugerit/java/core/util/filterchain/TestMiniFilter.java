package test.org.fugerit.java.core.util.filterchain;

import static org.junit.Assert.fail;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.filterchain.MiniFilterChain;
import org.fugerit.java.core.util.filterchain.MiniFilterConfig;
import org.fugerit.java.core.util.filterchain.MiniFilterContext;
import org.fugerit.java.core.util.filterchain.MiniFilterData;
import org.junit.Test;

public class TestMiniFilter {

	private static final String CONF_PATH = "core/util/filterchain/minifilter-test-config.xml";
	
	public static MiniFilterConfig init() {
		MiniFilterConfig config = new MiniFilterConfig();
		try {
			MiniFilterConfig.loadConfig( ClassHelper.loadFromDefaultClassLoader( CONF_PATH ) , config );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
		return config;
	}
	
	private static MiniFilterConfig CONFIG = init();
	
	public void testWorker( String chainId ) {
		try {
			System.out.println( "TEST START >>> "+chainId );
			MiniFilterChain chain = CONFIG.getChainCache( chainId );
			MiniFilterContext context = new MiniFilterContext();
			MiniFilterData data = new MiniFilterData() {};
			int res = chain.apply( context , data );
			System.out.println( "TEST END >>> "+chainId+" >> res:"+res );
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Error : "+e.getMessage() );	
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

}
