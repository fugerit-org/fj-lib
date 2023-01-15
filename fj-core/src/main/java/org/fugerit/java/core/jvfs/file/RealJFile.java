package org.fugerit.java.core.jvfs.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.helpers.AbstractJFile;

public class RealJFile extends AbstractJFile {
	
	@Override
    public String toString() {
        return super.toString()+"[file:"+this.file+"]";
    }
    
    private File file;		// the real file this JFile is base on

    public RealJFile(String path, JVFS jvfs, File file) {
        super(path, jvfs);
        this.file = file;
    }
    
    @Override
    public boolean create() throws IOException {
        return this.file.createNewFile();
    }

    @Override
    public boolean delete() throws IOException {
        return this.file.delete();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return (new FileInputStream(this.file));
    }

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return (new FileOutputStream(this.file));
    }

    @Override
    public boolean isFile() throws IOException {
        return this.file.isFile();
    }

    @Override
    public boolean mkdir() throws IOException {
        return this.file.mkdir();
    }

    @Override
    public boolean mkdirs() throws IOException {
        return this.file.mkdirs();
    }

    @Override
    public boolean exists() throws IOException {
        return this.file.exists();
    }   
    
    @Override
    public boolean isCanRead() throws IOException {
        return this.file.canRead();
    }
    
    @Override

    public boolean isCanWrite() throws IOException {
        return this.file.canWrite();
    }

    @Override
    public long getLastModified() throws IOException {
        return this.file.lastModified();
    }
    
    @Override
    public void setLastModified(long time) throws IOException {
        this.file.setLastModified(time);
    }
    
    @Override
    public void setReadOnly( boolean readOnly ) throws IOException {
    	if ( readOnly ) {
    		this.file.setReadOnly();	
    	} else {
    		this.file.setWritable( true );
    	}
    }

	@Override
	public JFile[] listFiles() throws IOException {
		File[] list = this.file.listFiles();
		JFile[] res = new JFile[ list.length ];
		for ( int k=0; k<list.length; k++ ) {
			res[k] = this.getChild( list[k].getName() );
		}
		return res;
	}
    
}
