package test.org.fugerit.java.core.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.xml.XMLClean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.helpers.io.ReaderFail;

@Slf4j
class TestCleanXML {

	private void copy( File sourceBase, File fileBase ) throws FileNotFoundException, IOException {
		for ( File current : sourceBase.listFiles( f -> f.isFile() ) ) {
			File dest = new File( fileBase, current.getName() );
			try ( FileInputStream is = new FileInputStream( current );
					FileOutputStream os = new FileOutputStream( dest ) ) {
				StreamIO.pipeStream(is, os, StreamIO.MODE_CLOSE_NONE);
				log.info( "dest {}", dest );
			}
		}
	}
	
	@Test
	void testClean() throws IOException {
		// prepare test data
		File sourceBase = new File( "src/test/resources/core/xml/dtd" );
		File fileBase = new File( "target/dtd" );
		File fileBaseSec = new File( fileBase, "sec" );
		if ( !fileBaseSec.exists() ) {
			log.info( "mkdirs {} -> {}", fileBaseSec, fileBaseSec.mkdirs() );
		}
		this.copy(sourceBase, fileBase);
		this.copy(sourceBase, fileBaseSec);
		// test
		XMLClean.cleanFolder( fileBase , "doc" );
	}
	
	@Test 
	public void testFailClean() throws IOException {
		try ( Reader reader = new ReaderFail();
				Writer writer = new StringWriter() ) {
			Assertions.assertThrows( ConfigRuntimeException.class , () -> XMLClean.cleanStream( reader, writer ) );
		}
	}
	
	@Test 
	public void testFailFolder() throws IOException {
		try ( Reader reader = new ReaderFail();
				Writer writer = new StringWriter() ) {
			Assertions.assertThrows( ConfigRuntimeException.class , () -> XMLClean.cleanFolder(null, null) );
		}
	}
	
	@Test 
	public void testCleanString() throws IOException {
		Assertions.assertNotNull( XMLClean.cleanXML( "test" ) );
	}
	
}

