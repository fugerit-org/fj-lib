package test.org.fugerit.java.core.io.line;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.List;

import org.fugerit.java.core.io.line.LineIOUtils;
import org.fugerit.java.core.io.line.LineReader;
import org.fugerit.java.core.io.line.LineWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLineIOUtils {

	private static final String TEST_FILE_PATH = "src/test/resources/core/db/base-db-conn.properties";
	
	private static final int EXPECTED_SIZE = 9;
	
	@Test
	public void testLineReadStream() throws IOException {
		try ( InputStream is = new FileInputStream( TEST_FILE_PATH ) ) {
			List<String> lines = LineIOUtils.readLines( is );
			Assertions.assertEquals( EXPECTED_SIZE , lines.size() );
		} 
	}
	
	@Test
	public void testLineReadReader() throws IOException {
		try ( Reader is = new FileReader( TEST_FILE_PATH ) ) {
			List<String> lines = LineIOUtils.readLines( is );
			Assertions.assertEquals( EXPECTED_SIZE , lines.size() );
		} 
	}
	
	@Test
	public void testLineReadFile() throws IOException {
		List<String> lines = LineIOUtils.readLines( new File( TEST_FILE_PATH ) );
		lines.stream().forEach( s -> log.info( "current line -> {}", s ) );
		String[] array = LineIOUtils.toLines(lines);
		Assertions.assertEquals( EXPECTED_SIZE , array.length );
	}
	
	private int testLineReader( LineReader reader ) throws IOException {
		String line = null;
		int count = 0;
		do {
			line = reader.readLine();
			if ( line != null ) {
				count ++;
			}
		} while ( line != null );
		return count;
	}
	
	@Test
	public void testCreateLineReader() throws IOException {
		try ( Reader is = new FileReader( TEST_FILE_PATH );
				LineReader reader = LineIOUtils.createLineReader( is ) ) {
			int count = this.testLineReader(reader);
			Assertions.assertEquals( EXPECTED_SIZE , count );
		} 
	}
	
	@Test
	public void testCreateLineBufferedReader() throws IOException {
		try ( Reader is = new BufferedReader( new FileReader( TEST_FILE_PATH ) );
				LineReader reader = LineIOUtils.createLineReader( is ) ) {
			int count = this.testLineReader(reader);
			Assertions.assertEquals( EXPECTED_SIZE , count );
		} 
	}
	
	@Test
	public void testCreateLineReaderStream() throws IOException {
		try ( InputStream is = new FileInputStream( TEST_FILE_PATH );
				LineReader reader = LineIOUtils.createLineReader( is ) ) {
			int count = this.testLineReader(reader);
			Assertions.assertEquals( EXPECTED_SIZE , count );
		} 
	}
	
	private static final String[] TEST_LINES = { "test1", "test2", "test3" };
	
	private int testLineWriter( LineWriter writer ) {
		int count = 0;
		for ( String line : TEST_LINES ) {
			writer.println(line);
			count++;
		}
		writer.print("END");
		writer.println();
		return count;
	}
	
	@Test
	public void testCreateLineWriter() throws IOException {
		try ( StringWriter w = new StringWriter();
				LineWriter writer = LineIOUtils.createLineWriter( w ) ) {
			int count = this.testLineWriter(writer);
			Assertions.assertEquals( TEST_LINES.length , count );
		}
	}
	
	@Test
	public void testCreateLineWriterStream() throws IOException {
		try ( ByteArrayOutputStream w = new ByteArrayOutputStream();
				LineWriter writer = LineIOUtils.createLineWriter( w ) ) {
			int count = this.testLineWriter(writer);
			Assertions.assertEquals( TEST_LINES.length , count );
		}
	}
	
}
