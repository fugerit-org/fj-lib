package test.org.fugerit.java.core.jvfs;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Date;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestRealJFile extends BasicTest {

	private static JVFS jvfs = TestJMountHelper.getSafeRealJVFS( "target/test_real_j_file" );
	
	@Test
	public void testFullFile() {
		try {
			JFile file = jvfs.getJFile( "/test_file.txt" );	// the file for my test
			log.info( "file to test : {}", file );
			log.info( "delete file if exists start : {}", file.delete() );	
			Assert.assertFalse( file.exists() );		// now it does not exists
			String content = "test file "+new Date();
			StreamIO.pipeChar( new StringReader( content), file.getWriter(), StreamIO.MODE_CLOSE_BOTH );
			Assert.assertTrue( file.isFile() );			// now it is file
			Assert.assertTrue( file.exists() );			// now exists
			// last modified
			long lastModified = System.currentTimeMillis()-10000L;
			file.setLastModified(lastModified);
			Assert.assertEquals( lastModified , file.getLastModified() );
			// check content
			String readContent = null;
			try ( Reader reader = file.getReader() ) {
				readContent = StreamIO.readString( reader );
			}
			log.info( "content expected : {}, found : {}", content, readContent );
			Assert.assertEquals( content , readContent );
			boolean deleteFile = file.delete();
			log.info( "delete file if exists end : {}", deleteFile );
			Assert.assertTrue( deleteFile );			// now exists
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testFullDirectory() {
		try {
			JFile file = jvfs.getJFile( "/test_dir/" );	// the file for my test
			log.info( "directory to test : {}", file );
			log.info( "delete directory if exists start : {}", file.delete() );	
			Assert.assertFalse( file.exists() );		// now it does not exists
			log.info( "create directory : {}", file.mkdirs() );
			Assert.assertTrue( file.isDirectory() );	// now it is directory
			Assert.assertTrue( file.exists() );			// now exists
			boolean deleteFile = file.delete();
			log.info( "delete directory if exists end : {}", deleteFile );
			Assert.assertTrue( deleteFile );			// now exists
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testCreateRename() {
		try {
			JFile file = jvfs.getJFile( "/test_start.txt" );	// the file for my test
			log.info( "file to test : {}", file );
			log.info( "delete file if exists start : {}", file.delete() );	
			Assert.assertFalse( file.exists() );		// now it does not exists
			log.info( "create : {}", file.create() );
			Assert.assertTrue( file.isFile() );			// now it is file
			Assert.assertTrue( file.exists() );			// now exists
			
			// rename
			JFile fileRename = jvfs.getJFile( "/test_renamed.txt" );	// the file to test rename
			boolean rename = file.rename( fileRename );
			Assert.assertTrue( rename );			// rename ok
			boolean deleteRename = fileRename.delete();
			log.info( "delete renamed file if exists end : {}", deleteRename );
			Assert.assertTrue( deleteRename );			// delete ok
			
			boolean deleteFile = file.delete();
			log.info( "delete file if exists end : {}", deleteFile );
			Assert.assertFalse( deleteFile );			// should not exist
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	@Test
	public void testRenameFail() {
		try {
			JFile file = jvfs.getJFile( "/test_rename_file.txt" );	// the file for my test
			log.info( "file to test : {}", file );
			log.info( "delete file if exists start : {}", file.delete() );	
			Assert.assertFalse( file.exists() );		// now it does not exists
			log.info( "create : {}", file.create() );
			Assert.assertTrue( file.isFile() );			// now it is file
			Assert.assertTrue( file.exists() );			// now exists
			JFile renameToFile = new StubJFile( "/stub.txt" ) ;
			Assert.assertThrows( IOException.class , () -> {
				boolean rename = file.rename( renameToFile );
				log.info( "renamed? : {}", rename );
				fail( "Should throw an exception before!" );
			});	
		} catch (IOException e) {
			this.failEx(e);
		}
	}

	@Test
	public void testSetReadonly() {
		JFile file = jvfs.getJFile( "/test_readonly.txt" );
		try {
			// create the file
			Assert.assertTrue( file.create() );
			file.setReadOnly( true );
			log.info( "readonly file {} : {}", file, file.isCanRead() );
			// test readonly
			Assert.assertThrows( IOException.class , () -> {
				try ( Writer writer = file.getWriter() ) {
					log.info( "writer : {}", writer );
				}
			} );
		} catch (IOException e) {
			this.failEx(e);
		} finally {
			try {
				// cleanup
				file.setReadOnly( false );
				log.info( "writeable file {} : {}", file, file.isCanWrite() );
				Assert.assertTrue( file.delete() );
				// further testing
				JFile notExist = jvfs.getJFile( "/test_readnly_not_exist" );
				notExist.setReadOnly( true );
				notExist.setReadOnly( false );
				notExist.setLastModified( System.currentTimeMillis() );
			} catch (IOException e) {
				this.failEx(e);
			}
		}
	}
	
}
