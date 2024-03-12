package org.fugerit.java.core.jvfs.db.daogen.junit4test.model;

import org.fugerit.java.core.jvfs.db.daogen.helper.HelperDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.helper.WrapperDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.model.ModelDbJvfsFile;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * DbJvfsFileJunit4ModelTest, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public class DbJvfsFileJunit4ModelTest {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	private static final Logger logger = LoggerFactory.getLogger( DbJvfsFileJunit4ModelTest.class );

	public void printAll( ModelDbJvfsFile current ) { 
		 logger.info( "FILE_NAME-> {}", current.getFileName() );
		 logger.info( "PARENT_PATH-> {}", current.getParentPath() );
		 logger.info( "FILE_PROPS-> {}", current.getFileProps() );
		 logger.info( "CREATION_TIME-> {}", current.getCreationTime() );
		 logger.info( "UPDATE_TIME-> {}", current.getUpdateTime() );
		 logger.info( "FILE_SIZE-> {}", current.getFileSize() );
		 logger.info( "FILE_CONTENT-> {}", current.getFileContent() );
	}

	public ModelDbJvfsFile newInstance() { 
		WrapperDbJvfsFile current = new WrapperDbJvfsFile( new HelperDbJvfsFile() );
		Assert.assertTrue( current.isEmpty() );
		current.setFileName("1");
		Assert.assertFalse( current.isEmpty() );
		current.setParentPath("1");
		Assert.assertFalse( current.isEmpty() );
		current.setFileProps("1");
		Assert.assertFalse( current.isEmpty() );
		current.setCreationTime(new java.util.Date());
		Assert.assertFalse( current.isEmpty() );
		current.setUpdateTime(new java.util.Date());
		Assert.assertFalse( current.isEmpty() );
		current.setFileSize(new java.math.BigDecimal( "1" ));
		Assert.assertFalse( current.isEmpty() );
		current.setFileContent(null);
		Assert.assertFalse( current.isEmpty() );
		logger.info( "unwrap :  {}", current.unwrap( current ) );
		return current;
	}

	@Test
	public void testJUnit4ModelDbJvfsFile() { 
		ModelDbJvfsFile current = this.newInstance();
		this.printAll( current );
		logger.info( "current toString() : {}", current );
		Assert.assertNotNull( current );
	}

}
