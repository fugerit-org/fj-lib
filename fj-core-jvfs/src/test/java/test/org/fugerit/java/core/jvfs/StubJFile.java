package test.org.fugerit.java.core.jvfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.helpers.AbstractJFile;

public class StubJFile extends AbstractJFile {

	public StubJFile( String path ) throws IOException {
		this( path, TestJMountHelper.getRealJVFSDefault() );
	}
	
	protected StubJFile(String path, JVFS jvfs) {
		super(path, jvfs);
	}

	@Override
	public JFile[] listFiles() throws IOException {
		// stub implementation for tests
		return null;
	}

	@Override
	public boolean delete() throws IOException {
		// stub implementation for tests
		return false;
	}

	@Override
	public boolean create() throws IOException {
		// stub implementation for tests
		return false;
	}

	@Override
	public boolean exists() throws IOException {
		// stub implementation for tests
		return false;
	}

	@Override
	public boolean mkdir() throws IOException {
		// stub implementation for tests
		return false;
	}

	@Override
	public boolean mkdirs() throws IOException {
		// stub implementation for tests
		return false;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// stub implementation for tests
		return null;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		// stub implementation for tests
		return null;
	}

	@Override
	public boolean isCanRead() throws IOException {
		// stub implementation for tests
		return false;
	}

	@Override
	public boolean isCanWrite() throws IOException {
		// stub implementation for tests
		return false;
	}

	@Override
	public void setReadOnly(boolean readOnly) throws IOException {
		// stub implementation for tests
		
	}

	@Override
	public long getLastModified() throws IOException {
		// stub implementation for tests
		return 0;
	}

	@Override
	public void setLastModified(long time) throws IOException {
		// stub implementation for tests
		
	}

	@Override
	public boolean rename(JFile newFile) throws IOException {
		// stub implementation for tests
		return false;
	}

}
