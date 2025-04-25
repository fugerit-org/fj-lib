package test.org.fugerit.java.core.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.FileFun;
import org.fugerit.java.core.io.FileIO;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.io.file.AbstractFileFun;
import org.fugerit.java.core.io.file.DeleteRecurseFileFun;
import org.fugerit.java.core.io.file.FileFunSecure;
import org.fugerit.java.core.io.file.FileFunWrapper;
import org.fugerit.java.core.io.file.ZipArchiveDirFileFun;
import org.fugerit.java.core.io.file.ZipFileFun;
import org.fugerit.java.core.lang.helpers.ExHandlerDefault;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.BasicTest;

@Slf4j
public class TestFileFun extends BasicTest {

	private boolean worker( String testPath, String sourcePath ) {
		boolean ok = false;
		try {
			File testDir = new File( testPath );
			if ( !testDir.exists() ) {
				log.info( "mkdirs : {} -> {}", testDir, testDir.mkdirs() );
			}
			// copy file fun
			File sourceFile = new File(sourcePath);
			FileIO.recurseDir( sourceFile , AbstractFileFun.newFileFun( (f) -> {
				SafeFunction.apply( () -> {
					String relPath = f.getCanonicalPath().substring( sourceFile.getCanonicalPath().length() );
					File newFile = new File( testDir, relPath );
					log.info( "from {} to {}", f, newFile );
					if ( f.isFile() ) {
						try ( FileInputStream fis = new FileInputStream( f );
								FileOutputStream fos = new FileOutputStream( newFile ) ) {
							StreamIO.pipeStream( fis, fos, StreamIO.MODE_CLOSE_NONE );
						}
					} else {
						newFile.mkdir();
					}
				} );
			}));
			// archive file fun
			try ( ZipArchiveDirFileFun fun = new ZipArchiveDirFileFun(testDir, new File( "target/test.zip" )) ) {
				FileIO.recurseDir( testDir , fun );	
			}
			// delete file fun
			try ( DeleteRecurseFileFun fun = new DeleteRecurseFileFun() ) {
				FileIO.recurseDir( testDir , fun );	
			}
			// alternative zip file
			FileIO.zipFile( new File( "target/alt_zip0.zip" ) , testDir );
			FileIO.zipFileSecure( new File( "target/alt_zip1.zip" ) , testDir );
			FileIO.zipFileSecure( new File( "target/alt_zip2.zip" ) , testDir , new ExHandlerDefault() );
			ok = true;
		} catch (Exception e) {
			this.failEx(e);
		}
		return ok;
	}
	
	private boolean worker( FileFun fun ) throws IOException {
		String test = "/test/test.txt";
		File testFile = new File( test );
		fun.handleFile( test );
		fun.handleFile( testFile );
		fun.handleFile( testFile.getParentFile() , testFile.getName() );
		fun.handleFile( testFile.getParent() , testFile.getName() );
		return fun != null;
	}
	
	@Test
	public void testFun() {
		boolean ok = this.worker( "target/file_fun", "src/test/resources/core/xml/dtd" );
		Assertions.assertTrue(ok);
	}
	
	@Test
	public void testAlt() throws IOException {
		try ( FileFun fun =  AbstractFileFun.newFileFun( f -> log.info( "worker : {}", f )) ) {
			FileFunWrapper wrapper = new FileFunWrapper(fun);
			FileFunSecure secure = new FileFunSecure(wrapper, new ExHandlerDefault());
			boolean ok = this.worker( secure );	
			log.info( "secure wrapped : {}", secure.getWrappedFileFun() );
			Assertions.assertTrue(ok);
			FileFunSecure.apply( new ExHandlerDefault() ,  () -> new IOException( "TEST" ) );
		}
	}
	
	@Test
	public void testZip() throws IOException {
		boolean ok = false;
		File sourceDir = new File( "src/test/resources/core/xml/dtd" );
		try ( ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( new File( "target/zip_fun_test.zip" ) ) );
				ZipFileFun fun = new ZipFileFun(zos, sourceDir) ) {
			fun.handleFile( sourceDir.listFiles()[0] );
			ok = true;
		}
		Assertions.assertTrue( ok );
	}
	
}
