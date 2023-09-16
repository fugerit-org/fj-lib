package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import org.fugerit.java.core.io.FileFun;

/*
 * 
 * 
 * @author Fugerit
 *
 */
public abstract class AbstractFileFun implements FileFun {

    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File, java.lang.String)
     */
    @Override
    public void handleFile(File parent, String name) throws IOException {
        this.handleFile(new File(parent, name));
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String, java.lang.String)
     */
    @Override
    public void handleFile(String parent, String name) throws IOException {
        this.handleFile(new File(parent), name);
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String)
     */
    @Override
    public void handleFile(String path) throws IOException {
        this.handleFile(new File(path));
    }
    
	@Override
	public void close() throws IOException {
		// usually is a good idea to implement this method.
	}
	
	public static FileFun newFileFun( Consumer<File> c ) {
		return new AbstractFileFun() {	
			@Override
			public void handleFile(File file) throws IOException {
				c.accept(file);
			}
		};
	}
    
}
