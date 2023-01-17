package org.fugerit.java.core.jvfs.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.daogen.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.helpers.SimpleAbstractJFile;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class DaogenJFileDB extends SimpleAbstractJFile {

	public static final String FLAG_RO = "RO;";
	
	private ModelDbJvfsFile dbFile;
	
	private JMountDaogenDB jMount;
	
	private static long updateTime( ModelDbJvfsFile model ) {
		long time = Long.MIN_VALUE;
		if ( model != null ) {
			time = model.getUpdateTime().getTime();
		}
		return time;
	}
	
	public static boolean checkFlag( ModelDbJvfsFile dbFile, String flag ) {
		boolean res = false;
		if ( dbFile != null ) {
			res = StringUtils.valueWithDefault( dbFile.getFileProps() , "").contains( flag );
		}
		return res;
	}
	
	public DaogenJFileDB(String path, JVFS jvfs, ModelDbJvfsFile dbFile, JMountDaogenDB jMount) {
		super(path, jvfs, updateTime(dbFile), true, !checkFlag(dbFile, FLAG_RO) );
		this.dbFile = dbFile;
		this.jMount = jMount;
	}

	@Override
	public JFile[] listFiles() throws IOException {
		return this.getjMount().listFiles( this );
	}

	@Override
	public boolean delete() throws IOException {
		return this.getjMount().delete( this.dbFile );
	}

	@Override
	public boolean create() throws IOException {
		return false;
	}

	@Override
	public boolean exists() throws IOException {
		return this.dbFile != null;
	}

	@Override
	public boolean mkdir() throws IOException {
		return this.jMount.mkdir( this, false );
	}

	@Override
	public boolean mkdirs() throws IOException {
		return this.jMount.mkdir( this, true );
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.jMount.streamIn(this.dbFile);
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return this.jMount.streamOut(this);
	}

	protected void setDbFile( ModelDbJvfsFile dbFile ) {
		this.dbFile = dbFile;
	}
	
	public ModelDbJvfsFile getDbFile() {
		return dbFile;
	}

	public JMountDaogenDB getjMount() {
		return jMount;
	}
	
}
