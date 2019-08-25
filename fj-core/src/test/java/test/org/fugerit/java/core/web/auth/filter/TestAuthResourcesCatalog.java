package test.org.fugerit.java.core.web.auth.filter;

import java.io.InputStream;

import org.fugerit.java.core.cfg.xml.ListMapConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.web.auth.filter.AuthResourcesConfig;
import org.fugerit.java.core.web.auth.filter.AuthResourcesEntry;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestAuthResourcesCatalog extends BasicTest {
	
	public static final String TEST_CONFIG_PATH = "core/web/auth/resource/auth-resouces.xml";
	
	@Test
	public void testLoadConfig() throws Exception {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( TEST_CONFIG_PATH ) ) {
			AuthResourcesConfig config = AuthResourcesConfig.loadConfig( is );
			for ( String currentId : config.getIdSet() ) {
				logger.info( "current chain : "+currentId );
				ListMapConfig<AuthResourcesEntry> chain = config.getListMap( currentId );
				for ( AuthResourcesEntry entry : chain ) {
					logger.info( "current entry : "+entry );
				}
			}
			AuthResourcesEntry test1Entry = config.match( "/test/test01/prova1" );
			logger.info( "Found? : "+test1Entry );
		}
	}
	
}
