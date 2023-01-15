package test.org.fugerit.java.core.jvfs;

import org.fugerit.java.core.jvfs.helpers.JFileUtils;
import org.fugerit.java.core.jvfs.helpers.PathDescriptor;
import org.junit.Assert;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestPathDescriptor extends BasicTest {

	private void testWorker( String path, String parentPathExpected, String nameExpected ) {
		try {
			PathDescriptor descriptor = JFileUtils.pathDescriptor( path );
			logger.info( "path:{}, descriptor:{}", path, descriptor );
			Assert.assertEquals( "Expected name" , nameExpected, descriptor.getName() );
			Assert.assertEquals( "Expected parent path" , parentPathExpected, descriptor.getParentPath() );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void test01Root() {
		this.testWorker( "/", null, "/" );
	}
	
	@Test
	public void test02() {
		this.testWorker( "/test/", "/", "test/" );
	}

	@Test
	public void test03() {
		this.testWorker( "/test/core/check.txt", "/test/core/", "check.txt" );
	}
	
	@Test
	public void test04() {
		this.testWorker( "/test/check.txt", "/test/", "check.txt" );
	}
	
	@Test
	public void test05() {
		this.testWorker( "/test/core/file/check.xml", "/test/core/file/", "check.xml" );
	}
	
	@Test
	public void test06() {
		this.testWorker( "/test/core/file/", "/test/core/", "file/" );
	}
	
}
