package test.org.fugerit.java.core.cfg;

import org.fugerit.java.core.cfg.VersionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestVersionUtils {

	private static final String NAME = HelperVersion.NAME;
	
	private static final String VERSION_STRING = "test 1.0.0 2023-09-08";
	
	@Test
	void testModuleVersion() {
		VersionUtils.registerModule( NAME , HelperVersion.class.getName() );
		VersionUtils.registerModule( "not-loaded" , "test.not.Loaded" );
		VersionUtils.registerModule( "wrong-type" , "test.org.fugerit.java.core.cfg.TestVersionUtils" );
		Assertions.assertEquals( VERSION_STRING, VersionUtils.getVersionString( NAME ) );
		Assertions.assertEquals( VersionUtils.CODE_01_NOT_FOUND, VersionUtils.getVersionString( "not-registered" ) );
		Assertions.assertEquals( "[02] Class module isn't loaded : (test.not.Loaded) - java.lang.ClassNotFoundException: test.not.Loaded", VersionUtils.getVersionString( "not-loaded" ) );
		Assertions.assertEquals( VersionUtils.CODE_03_NO_CONVERT, VersionUtils.getVersionString( "wrong-type" ) );
	}
	
}
