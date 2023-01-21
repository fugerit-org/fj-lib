package test.org.fugerit.java.core.jvfs.daogen;

import java.io.InputStream;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.daogen.base.config.DaogenFacade;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class JvfsDaogenRun extends BasicTest {
	
	public static final String DAOGEN_CONFIG = "core-jvfs/db/daogen/daogen-config-jvfs.xml";
	
	@Test
	public void testRunJvfsDB() {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( DAOGEN_CONFIG ) ) {
			DaogenFacade.generate( is );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
