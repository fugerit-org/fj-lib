package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.io.FileInputStream;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.fugerit.java.core.jvfs.db.daogen.helper.HelperDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.helper.WrapperDbJvfsFile;
import org.fugerit.java.core.jvfs.db.impl.facade.data.DataEntityDbJvfsFileFacade;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.jvfs.db.helper.MemJvfsDBHelper;

@Slf4j
public class TestDBJMount extends TestJVFSHelper {

	@Test
	public void testInitMemDB() {
		try  {
			String tableName = "FUGERIT.ALT2_DB_JVFS_FILE";
			DataEntityDbJvfsFileFacade facade = DataEntityDbJvfsFileFacade.newInstanceWithTable(tableName);
			JVFS jvfs =  JMountDaogenDB.createJVFSWithTableName( MemJvfsDBHelper.newCF(), tableName, () -> new WrapperDbJvfsFile( new HelperDbJvfsFile() ) );
			this.testCreateDelete( jvfs );
			File testCopy = new File( "src/main/resources/core-jvfs/daogen/sql/JMountDaogenDB.md" );
			JFile testFile = jvfs.getRoot().getChild( "test-mount/core-jvfs/JMountDaogenDB.md" );
			StreamIO.pipeStream( new FileInputStream( testCopy ) , testFile.getOutputStream(), StreamIO.MODE_CLOSE_BOTH );
			this.tryDumpTestDb( facade );
			JFile newFile = jvfs.getJFile( "/test-mount/core-jvfs/JMountDaogenDB_renamed.md" );
			boolean testRename = testFile.rename( newFile );
			Assert.assertTrue( testRename );
			JFile newDir = jvfs.getJFile( "/core-jvfs-renamed/" );
			boolean testRenameDir = testFile.getParent().rename( newDir );
			Assert.assertTrue( testRenameDir );
			this.tryDumpTestDb( facade );
			log.info( "toString() : {}", testFile );
			WrapperDbJvfsFile wrapper = new WrapperDbJvfsFile( new HelperDbJvfsFile() );
			log.info( "toString() : {}", wrapper );
			log.info( "isEmpty() : {}", wrapper.isEmpty() );
			log.info( "setTest1() : {}", this.fullSerializationTest( wrapper ) );
			log.info( "setTest2() : {}", this.fullSerializationTest( wrapper.unwrap() ) );
		} catch (Exception e) {
			failEx(e);
		}
	}
	
}
