package test.org.fugerit.java.core.jvfs.db.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.JVFSImpl;
import org.fugerit.java.core.jvfs.helpers.AbstractJFile;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestAbstractJFile {

	@Test
	public void test1() throws IOException {
		File baseFolder = new File( "target/test_abstract_j_file" );	
		if ( !baseFolder.exists() ) {
			baseFolder.mkdirs();
		}
		JVFS jvfs = TestJMount.createJVFS( baseFolder );
		JFile file = jvfs.getJFile( "/abstrat.txt" );
		String test = "test";
		try ( Reader reader = new StringReader( test );
				Writer writer = file.getWriter() ) {
			StreamIO.pipeChar( reader , writer, StreamIO.MODE_CLOSE_NONE );
		}
		try ( Reader reader = file.getReader() ) {
			String testRead = StreamIO.readString( reader );
			Assert.assertEquals( test , testRead );
		}
		log.info( "describe : {}", file.describe() );
		Assert.assertEquals( test.length() , file.getSupposedSize()  );
		Arrays.asList( jvfs.getRoot().list() ).stream().forEach( f -> log.info( "kid : {}", f ) );
		log.info( "normalized path : {}", AbstractJFile.normalizePath( file.getPath() ) );
		log.info( "normalized path : {}", AbstractJFile.normalizePath( jvfs.getRoot().getPath() ) );
		Assert.assertTrue( file.isFile() );
		Assert.assertTrue( jvfs.getRoot().isDirectory() );
	}
	
}

class TestJMount implements JMount {

	public static JVFS createDefaultJVFS() {
		JVFS jvfs = null;
		try {
			jvfs = createJVFS(new File("/"));
		} catch (IOException e) {
			throw ConfigRuntimeException.convertExMethod( "createDefaultJVFS", e );
		}
		return jvfs;
	}

	public static JVFS createJVFS(File root) throws IOException {
		return JVFSImpl.getInstance(new TestJMount(root));
	}

	public String toString() {
		return this.getClass().getSimpleName() + "[base:" + this.base + "]";
	}

	private File base; // the base path for the JMount

	public TestJMount(File base) {
		this.base = base;
	}

	@Override
	public JFile getJFile(JVFS jvfs, String point, String relPath) {
		File file = new File(base, relPath);
		if ( point.endsWith( JFile.SEPARATOR ) ) {
			point = point.substring( 0, point.length()-JFile.SEPARATOR.length() );
		}
		String path = point+relPath;
		if ( file.isDirectory() && !path.endsWith( JFile.SEPARATOR ) ) {
			path+=JFile.SEPARATOR;
		}
		return (new TestJFile(path, jvfs, file));
	}

}


class TestJFile extends AbstractJFile {

	private File file;
	
	protected TestJFile(String path, JVFS jvfs, File file) {
		super(path, jvfs);
		this.file = file;
	}

	@Override
	public JFile[] listFiles() throws IOException {
		return Arrays.asList( this.file.listFiles() ).stream().map( f -> 
			SafeFunction.get( () -> this.getChild( f.getName() ) )
		).collect( Collectors.toList() ).toArray( new JFile[0] );
	}

	@Override
	public boolean delete() throws IOException {
		// will be implemented if needed for tests
		return false;
	}

	@Override
	public boolean create() throws IOException {
		// will be implemented if needed for tests
		return false;
	}

	@Override
	public boolean exists() throws IOException {
		// will be implemented if needed for tests
		return false;
	}

	@Override
	public boolean mkdir() throws IOException {
		// will be implemented if needed for tests
		return false;
	}

	@Override
	public boolean mkdirs() throws IOException {
		// will be implemented if needed for tests
		return false;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(this.file);
	}

	@Override
	public boolean isCanRead() throws IOException {
		// will be implemented if needed for tests
		return false;
	}

	@Override
	public boolean isCanWrite() throws IOException {
		// will be implemented if needed for tests
		return false;
	}

	@Override
	public void setReadOnly(boolean readOnly) throws IOException {
		// will be implemented if needed for tests
		
	}

	@Override
	public long getLastModified() throws IOException {
		// will be implemented if needed for tests
		return 0;
	}

	@Override
	public void setLastModified(long time) throws IOException {
		// will be implemented if needed for tests
		
	}

	@Override
	public boolean rename(JFile newFile) throws IOException {
		// will be implemented if needed for tests
		return false;
	}
	
}
