package test.org.fugerit.java.core.jvfs.dao;

import java.io.IOException;

import org.fugerit.java.core.jvfs.db.daogen.def.facade.DbJvfsFileFinder;
import org.fugerit.java.core.jvfs.db.daogen.helper.HelperDbJvfsFile;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestDbJvfsFileFinder extends BasicTest {

	@Test
	public void testFinder() throws IOException {
		DbJvfsFileFinder finder = new DbJvfsFileFinder();
		finder.setModel( new HelperDbJvfsFile() );
		log.info( "log -> {}", DbJvfsFileFinder.newInstance( finder.getModel() ) );
		Assert.assertNotNull( this.fullSerializationTest( finder ) );
	}
	
}
