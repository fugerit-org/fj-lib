package org.fugerit.java.core.jvfs.helpers;

import java.io.IOException;

import org.fugerit.java.core.jvfs.JVFS;

public abstract class SimpleAbstractJFile extends AbstractJFile {

    private long lastModified;
    
    private boolean canRead;
    
    private boolean canWrite;
    
    protected SimpleAbstractJFile(String path, JVFS jvfs) {
        this(path, jvfs, System.currentTimeMillis(), true, true);
    }
    
    protected SimpleAbstractJFile(String path, JVFS jvfs, long lastModified, boolean canRead, boolean canWrite) {
        super(path, jvfs);
        this.lastModified = lastModified;
        this.canRead = canRead;
        this.canWrite = canWrite;
    }
    
    @Override 
    public boolean isCanRead() throws IOException {
    	return this.canRead;
    }
   
    @Override
    public boolean isCanWrite() throws IOException {
    	return this.canWrite;
    }

    @Override
    public long getLastModified() throws IOException {
    	return this.lastModified;
    }
    
    @Override
    public void setLastModified(long time) throws IOException {
    	this.lastModified = time;
    }
    
    @Override
	public void setReadOnly( boolean readOnly ) throws IOException {
    	this.canWrite = !readOnly;
	}

}
