package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.function.UnsafeVoid;
import org.fugerit.java.core.io.FileFun;
import org.fugerit.java.core.lang.helpers.ExHandler;

/*
 * 
 * 
 * @author Fugerit
 *
 */
public class FileFunSecure extends FileFunWrapper {

    public FileFunSecure(FileFun wrapped, ExHandler handler) {
        super(wrapped);
        this.handler = handler;
    }
    
    private ExHandler handler;	// handler for exception which
    							// can be thrown during execution

    public static void apply( ExHandler handler, UnsafeVoid<IOException> fun ) {
    	try {
			fun.apply();
		} catch (IOException e) {
			handler.error(e);
		}
    }
    
    private void handleWorker( UnsafeVoid<IOException> fun ) {
    	apply( this.handler, fun );
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File, java.lang.String)
     */
    @Override
    public void handleFile(File parent, String name) throws IOException {
    	this.handleWorker( () -> super.handleFile(parent, name) );
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File)
     */
    @Override
    public void handleFile(File file) throws IOException {
    	this.handleWorker( () -> super.handleFile(file) );
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String, java.lang.String)
     */
    @Override
    public void handleFile(String parent, String name) throws IOException {
    	this.handleWorker( () -> super.handleFile(parent, name) );
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String)
     */
    @Override
    public void handleFile(String path) throws IOException {
    	this.handleWorker( () -> super.handleFile(path) );
    }
    
}
