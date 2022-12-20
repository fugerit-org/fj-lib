package test.org.fugerit.java.core.lang.helpers;

import org.fugerit.java.core.lang.helpers.JavaVersionHelper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJavaVersionHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(TestJavaVersionHelper.class);

	@Test
	public void test() {
		int majorVersion = JavaVersionHelper.parseUniversalJavaMajorVersion();
		logger.info("major version -> '{}'", majorVersion);
		Assert.assertEquals( "Wrong java major version" , JavaVersionHelper.MAJOR_VERSION_FJ_CORE_REF, majorVersion );
	}

}
