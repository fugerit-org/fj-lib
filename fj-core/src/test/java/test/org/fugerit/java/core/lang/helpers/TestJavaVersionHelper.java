package test.org.fugerit.java.core.lang.helpers;

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
		Assert.assertEquals( "Wrong java major version" , JavaVersionHelper.MAJOR_VERSION_JAVA_11, majorVersion );
	}

}
