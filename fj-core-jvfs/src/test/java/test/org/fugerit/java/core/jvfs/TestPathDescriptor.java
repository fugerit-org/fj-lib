package test.org.fugerit.java.core.jvfs;

import org.fugerit.java.core.jvfs.helpers.JFileUtils;
import org.fugerit.java.core.jvfs.helpers.PathDescriptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;

class TestPathDescriptor extends BasicTest {

	private void testWorker( String path, String parentPathExpected, String nameExpected ) {
		try {
			PathDescriptor descriptor = JFileUtils.pathDescriptor( path );
			logger.info( "path:{}, descriptor:{}", path, descriptor );
			Assertions.assertEquals( nameExpected, descriptor.getName(), "Expected name" );
			Assertions.assertEquals( parentPathExpected, descriptor.getParentPath(), "Expected parent path" );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	 void test01Root() {
		this.testWorker( "/", "", "/" );
	}
	
	@Test
	 void test02() {
		this.testWorker( "/test/", "/", "test/" );
	}

	@Test
	 void test03() {
		this.testWorker( "/test/core/check.txt", "/test/core/", "check.txt" );
	}
	
	@Test
	 void test04() {
		this.testWorker( "/test/check.txt", "/test/", "check.txt" );
	}
	
	@Test
	 void test05() {
		this.testWorker( "/test/core/file/check.xml", "/test/core/file/", "check.xml" );
	}
	
	@Test
	 void test06() {
		this.testWorker( "/test/core/file/", "/test/core/", "file/" );
	}
	
}
