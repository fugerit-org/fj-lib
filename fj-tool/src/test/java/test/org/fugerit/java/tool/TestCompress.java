package test.org.fugerit.java.tool;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.fugerit.java.tool.compress.CompressUtils;
import org.fugerit.java.tool.compress.sevenz.SevenZArchiveDirFileFun;
import org.junit.Assert;
import org.junit.Test;

public class TestCompress {

	@Test
	public void compressSevenZip() throws IOException {
		boolean ok = false;
		File source = new File( "src/test/resources/tool/params" );
		File dest = new File( "target/seven_zip.7z" );
		CompressUtils.compress7Zipfile( source , dest );
		ok = true;
		Assert.assertTrue(ok);
	}
	
	@Test
	public void testCompressStream() throws IOException {
		File dest = new File( "target/seven_test_zip.7z" );
		try ( SevenZOutputFile sevenZipFile = new SevenZOutputFile( dest );
				OutputStream out = SevenZArchiveDirFileFun.newWrapperStream( sevenZipFile ) ) {
			SevenZArchiveEntry entry = sevenZipFile.createArchiveEntry( new File( "CharsetCorrect.txt" ) , "test.txt" );
			sevenZipFile.putArchiveEntry(entry);
			out.write( 10 );
			out.write( "test".getBytes() );
		}
		Assert.assertNotNull( dest );
	}
	
	@Test
	public void testFun() throws IOException {
		File target = new File( "target" );
		SevenZArchiveDirFileFun fun = new SevenZArchiveDirFileFun( new File( target, "input" ), new File( target, "output.gz" ) );
		Assert.assertNotNull(fun);
	}
	
}
