package org.fugerit.java.core.jvfs.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

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
    
    public AbstractJFile(String path, JVFS jvfs) {
        this.path = path;
        this.jvfs = jvfs;
    }

    @Override
    public JVFS getJVFS() {
        return this.jvfs;
    }

    @Override
    public String getName() {
    	int index = this.path.lastIndexOf( SEPARATOR );
    	String res = this.path;
    	if ( index != -1 ) {
    		res = this.path.substring( index+SEPARATOR.length() );
    	}
    	return res;
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
        return (new InputStreamReader(this.getInputStream()));
    }

    @Override
    public Writer getWriter() throws IOException {
        return (new OutputStreamWriter(this.getOutputStream()));
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
			String parentPath = this.getPath().substring( 0, this.getName().length() );
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
    
}
