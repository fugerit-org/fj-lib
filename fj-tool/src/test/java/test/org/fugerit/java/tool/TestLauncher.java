package test.org.fugerit.java.tool;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.tool.Launcher;
import org.fugerit.java.tool.ToolHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestLauncher {

	@Test
	void testHelp() {
		boolean ok = false;
		String[] params = { ArgUtils.getArgString( Launcher.ARG_HELP ), "1" };
		Launcher.main( params );
		ok = true;
		assertTrue( ok );
	}

	@Test
	void testToolNotFound() {
		int res = Launcher.handle( ArgHelper.readTestParams( "tool-not-found" ) );
		Assertions.assertNotEquals( ToolHandler.EXIT_OK , res);
	}
	
	@Test
	void testCharseCorrectHelp() {
		String[] test = { "charset-correct-help", "verbose", "verbose-help", "fixed-to-excel", "send-mail" };
		for ( String id : test ) {
			int res = Launcher.handle( ArgHelper.readTestParams( id ) );
			Assertions.assertEquals( ToolHandler.EXIT_OK , res);	
		}
		
	}
	
	@Test
	void testCharseCorrectNonUtf8() throws IOException {
		File outputDir = new File( "target/charset_correct_test1" );
		ArgHelper.deleteDir( outputDir );
		ArgHelper.copyDir( new File( "src/test/resources/tool/charset_correct" ) , outputDir );
		StreamIO.pipeStream( new GZIPInputStream( new FileInputStream( new File( "target/charset_correct_test1/test_no_utf8.gz" ) ) ) , 
				new FileOutputStream( new File( "target/charset_correct_test1/test_no_utf8.java" ) ) , StreamIO.MODE_CLOSE_BOTH);
		StreamIO.pipeStream( new GZIPInputStream( new FileInputStream( new File( "target/charset_correct_test1/test_no_utf8.gz" ) ) ) , 
				new FileOutputStream( new File( "target/charset_correct_test1/test_no_utf8.properties" ) ) , StreamIO.MODE_CLOSE_BOTH);		
		int res = Launcher.handle( ArgHelper.readTestParams( "charset-correct-non-utf8" ) );
		Assertions.assertEquals( ToolHandler.EXIT_OK , res);
	}
	
}
