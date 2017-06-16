package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.IOException;

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

    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File, java.lang.String)
     */
    public void handleFile(File parent, String name) throws IOException {
        try {
            super.handleFile(parent, name);    
        } catch (IOException ioe) {
            this.handler.error(ioe);
        }
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File)
     */
    public void handleFile(File file) throws IOException {
        try {
            super.handleFile(file);    
        } catch (IOException ioe) {
            this.handler.error(ioe);
        }
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String, java.lang.String)
     */
    public void handleFile(String parent, String name) throws IOException {
        try {
            super.handleFile(parent, name);    
        } catch (IOException ioe) {
            this.handler.error(ioe);
        }
    }
    
    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String)
     */
    public void handleFile(String path) throws IOException {
        try {
            super.handleFile(path);    
        } catch (IOException ioe) {
            this.handler.error(ioe);
        }
    }
    
}
