package test.org.fugerit.java.core.lang.helpers;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.JavaVersionHelper;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJavaVersionHelper {
	
	@Test
	public void test() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion();
		log.info("major version -> '{}'", majorVersion);
		boolean supportedBuildVersion = ( majorVersion == JavaVersionHelper.MAJOR_VERSION_JAVA_11 
				|| majorVersion == JavaVersionHelper.MAJOR_VERSION_JAVA_17
				|| majorVersion == JavaVersionHelper.MAJOR_VERSION_JAVA_21 ); 
		Assert.assertTrue( "Wrong java major version : "+majorVersion , supportedBuildVersion );
	}
	
	@Test
	public void test7() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion( "1.7.0_123" );
		log.info("major version -> '{}'", majorVersion);
		boolean supportedBuildVersion = ( majorVersion == 7 ); 
		Assert.assertTrue( "Wrong java major version : "+majorVersion , supportedBuildVersion );
	}
	
	@Test
	public void testUndefined() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion( "1.8X.0_362" );
		log.info("major version -> '{}'", majorVersion);
		boolean supportedBuildVersion = ( majorVersion == JavaVersionHelper.UNDEFINED ); 
		Assert.assertTrue( "Wrong java major version : "+majorVersion , supportedBuildVersion );
	}
	
	@Test
	public void test8() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion( "1.8.0_362" );
		log.info("major version -> '{}'", majorVersion);
		boolean supportedBuildVersion = ( majorVersion == JavaVersionHelper.MAJOR_VERSION_JAVA_8 ); 
		Assert.assertTrue( "Wrong java major version : "+majorVersion , supportedBuildVersion );
	}
	
	@Test
	public void test11() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion( "11.0.18" );
		log.info("major version -> '{}'", majorVersion);
		boolean supportedBuildVersion = ( majorVersion == JavaVersionHelper.MAJOR_VERSION_JAVA_11 ); 
		Assert.assertTrue( "Wrong java major version : "+majorVersion , supportedBuildVersion );
	}
	
	@Test
	public void test17() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion( "17.0.7" );
		log.info("major version -> '{}'", majorVersion);
		boolean supportedBuildVersion = ( majorVersion == JavaVersionHelper.MAJOR_VERSION_JAVA_17 ); 
		Assert.assertTrue( "Wrong java major version : "+majorVersion , supportedBuildVersion );
	}
	
	@Test
	public void test21() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion( "21" );
		log.info("major version -> '{}'", majorVersion);
		boolean supportedBuildVersion = ( majorVersion == JavaVersionHelper.MAJOR_VERSION_JAVA_21 ); 
		Assert.assertTrue( "Wrong java major version : "+majorVersion , supportedBuildVersion );
	}

	@Test
	public void testNull() {
		Assert.assertThrows( ConfigRuntimeException.class , () -> JavaVersionHelper.parseUniversalJavaMajorVersion( null ) );
	}
	
}
