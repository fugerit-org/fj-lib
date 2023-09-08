package test.org.fugerit.java.core.jvfs.fun;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JFileFacade;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.util.JFileUtilCP;
import org.fugerit.java.core.jvfs.util.JFileUtilRM;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.jvfs.TestJMountHelper;
import test.org.fugerit.java.core.jvfs.TestJVFSHelper;

@Slf4j
public class TestJFileUtils extends TestJVFSHelper {

	private JFile create() throws IOException {
		File file = new File(  "target/test/test_copy_fun_"+System.currentTimeMillis()  );
		file.delete();
		return TestJMountHelper.getSafeRealJVFS( file.getCanonicalPath() ).getRoot();
	}
	
	@Test
	public void testFunDelete() throws IOException {
		JFileUtilRM.delete( this.create() );
		JFileUtilRM.deleteRecurse( this.create() );
		JFileUtilRM.deleteRecurseForce( this.create() );
		JFileUtilRM.delete( this.create() , true );
		JFileUtilRM.delete( this.create() , true, true );
		int res = JFileUtilRM.delete( this.create() , true, true, true );
		log.info( "OK {}", res );
		Assert.assertEquals( 0, res );
	}
	
	@Test
	public void testFunCopy() throws IOException {
		JVFS jvfs = TestJMountHelper.getSafeRealJVFS( new File(  "target/test_copy_fun_01"  ).getCanonicalPath() );
		JFile source = jvfs.getJFile( "/test.txt" );
		StreamIO.pipeChar( new StringReader( "test" ) , source.getWriter() , StreamIO.MODE_CLOSE_BOTH );
		JFileUtilCP.copyFile( source , jvfs.getJFile( "/test_01.txt" ), false, true );
		JFileUtilCP.copyFile( source , jvfs.getJFile( "/test_01.txt" ), true, true );
		JFileUtilCP.copyFile( source , jvfs.getJFile( "/test_01.txt" ), true, true, true );
		JFileUtilCP.copyFileRecurseForce( source , jvfs.getJFile( "/test_01.txt" ) );
		JFileUtilCP.copyFileRecurseForceVerbose( source , jvfs.getJFile( "/test_01.txt" ) );
		Assert.assertThrows( IOException.class , () -> JFileUtilCP.copyFile( source , jvfs.getJFile( "/test_01.txt" ) ) );
		Assert.assertThrows( IOException.class , () -> JFileUtilCP.copyFileRecurse( source , jvfs.getJFile( "/test_01.txt" ) ) );
		JFileFacade.readBytes( source );
		JFileFacade.readString(source);
	}
	
}
