package org.fugerit.java.core.jvfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public interface JFile {

	public static final String SEPARATOR = "/";
	
    public JVFS getJVFS();

    public String getPath();
    
    public String getName();
    
    public JFile getChild(String name) throws IOException;
    
    public JFile getParent() throws IOException;
    
    public String[] list() throws IOException;
        
    public JFile[] listFiles() throws IOException;
    
    public List<JFile> lsFiles() throws IOException;
    
    public boolean delete() throws IOException;
    
    public boolean create() throws IOException;
    
    public boolean exists() throws IOException;

    public boolean mkdir() throws IOException;
    
    public boolean mkdirs() throws IOException;
    
    public boolean isDirectory() throws IOException;
    
    public boolean isFile() throws IOException;
    
    public InputStream getInputStream() throws IOException;
    
    public OutputStream getOutputStream() throws IOException;
    
    public Reader getReader() throws IOException;
    
    public Writer getWriter() throws IOException;
    
    public boolean isCanRead() throws IOException;
    
    public boolean isCanWrite() throws IOException;
    
    public void setReadOnly( boolean readOnly ) throws IOException;
    
	public int getSupposedSize() throws IOException;
    
	public long getLastModified() throws IOException;

	public void setLastModified(long time) throws IOException;
    
	public String describe();
	
}
