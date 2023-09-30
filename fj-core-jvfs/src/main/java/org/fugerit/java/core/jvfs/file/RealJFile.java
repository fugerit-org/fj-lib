package org.fugerit.java.core.jvfs.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.helpers.AbstractJFile;
import org.fugerit.java.core.jvfs.helpers.JFileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealJFile extends AbstractJFile {
	
	@Override
	public boolean rename(JFile newFile) throws IOException {
		File newF = null;
		newFile = JFileUtils.unwrapJFile( newFile );
		if ( newFile instanceof RealJFile ) {
			newF = ((RealJFile)newFile).file;
		} else {
			throw new IOException( "Cannot rename to : "+newFile );
		}
		return this.file.renameTo( newF );
	}

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
        return Files.deleteIfExists( this.file.toPath() );
    }

    @Override 
    public Reader getReader() throws IOException {
    	return new FileReader(this.file);
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return (new FileInputStream(this.file));
    }

    @Override
    public String getName() {
    	String name = this.file.getName();
    	try {
			if ( this.isDirectory() && !name.endsWith( SEPARATOR ) ) {
				name+= SEPARATOR;
			}
		} catch (IOException e) {
			throw new ConfigRuntimeException( e );
		}
        return name;
    }

    @Override
    public Writer getWriter() throws IOException {
    	return new FileWriter(this.file);
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
        boolean res = this.file.setLastModified(time);
        if ( !res ) {
        	log.warn( "false result setLastModified {} on {}", time, this.file );
        }
    }
    
    @Override
    public void setReadOnly( boolean readOnly ) throws IOException {
    	if ( readOnly ) {
    		boolean res = this.file.setReadOnly();	
    		if ( !res ) {
    			log.warn( "false result setReadOnly on {}", this.file );
    	    }
    	} else {
    		boolean res = this.file.setWritable( true );
    		if ( !res ) {
    			log.warn( "false result setWritable(true) on {}", this.file );
    	    }
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
