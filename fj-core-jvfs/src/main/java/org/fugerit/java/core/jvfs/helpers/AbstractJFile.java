package org.fugerit.java.core.jvfs.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;

public abstract class AbstractJFile implements JFile {
	
    @Override
	public String toString() {
		return this.getClass().getSimpleName()+"[path=" + path + "]";
	}
    
    public static String normalizePath( String path ) {
    	String res = path;
    	if ( path.endsWith( SEPARATOR ) ) {
    		res = path.substring( 0, path.length()-SEPARATOR.length() );
    	}
    	return res;
    }
    
    private String path;		// the file relative path
    
    private JVFS jvfs;			// the jvfs of the file
    
    protected AbstractJFile(String path, JVFS jvfs) {
        this.path = path;
        this.jvfs = jvfs;
    }

    @Override
    public JVFS getJVFS() {
        return this.jvfs;
    }

    @Override
    public String getName() {
    	return JFileUtils.pathDescriptor( this.getPath() ).getName();
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean isDirectory() throws IOException {
        return this.getPath().endsWith( SEPARATOR );
    }

    @Override
    public boolean isFile() throws IOException {
    	return !this.isDirectory();
    }

    @Override
    public String[] list() throws IOException {
    	JFile[] kids = this.listFiles();
    	String[] res = new String[kids.length];
    	for ( int k=0; k<kids.length; k++ ) {
    		res[k] = kids[k].getName();
    	}
    	return res;
    }

    @Override
    public Reader getReader() throws IOException {
    	Reader reader = null;
    	InputStream is = this.getInputStream();
    	if ( is != null ) {
    		reader = (new InputStreamReader(this.getInputStream()));
    	}
        return reader;
    }

    @Override
    public Writer getWriter() throws IOException {
    	Writer writer = null;
    	OutputStream os = this.getOutputStream();
    	if ( os != null ) {
    		writer = (new OutputStreamWriter(this.getOutputStream()));
    	}
        return writer;
    }

	@Override
    public int getSupposedSize() throws IOException {
    	int size = -1;
    	try ( InputStream is = this.getInputStream() ) {
    		size = is.available();
    	}
        return size;
    }

	@Override
	public JFile getParent() throws IOException {
		JFile parent = null;
		if ( !this.isRoot() ) {
			String newPath = this.getPath();
			String name = this.getName();
			String parentPath = path.substring( 0, newPath.length()-name.length() );
			parent = this.getJVFS().getJFile( parentPath );
		}
		return parent;
	}
	
	@Override
	public JFile getChild(String name) throws IOException {
		return this.getJVFS().getJFile( this.getPath()+name );
	}

	public boolean isRoot() {
		return SEPARATOR.equals( this.getPath() );
	}

	@Override
	public List<JFile> lsFiles() throws IOException {
		return Arrays.asList( this.listFiles() );
	}

	@Override
	public String describe() {
		// see SafeFunction.get() [if any Exception is thrown, it will be wrapped by a ConfigRuntimeException]
		return SafeFunction.get( () -> this.getClass().getSimpleName()+"[path:"+this.getPath()+",isDirectory:"+this.isDirectory()+",isFile:"+this.isFile()+"]" );
	}
	
}
