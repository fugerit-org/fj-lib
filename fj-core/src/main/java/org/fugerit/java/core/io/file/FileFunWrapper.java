package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.io.FileFun;

/*
 * <p>
 * 		Wrapper class for a <code>FileFun</code>.
 * 		Can be used to add functionalities to
 * 		another <code>FileFun</code>.
 * </p>
 *
 * @author Fugerit
 */
public class FileFunWrapper implements FileFun {

    private FileFun wrappedFileFun;		// the FileFun to be wrapped
    
    public FileFunWrapper(FileFun wrappedFileFun) {
        super();
        this.wrappedFileFun = wrappedFileFun;
    }

    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String, java.lang.String)
     */
    public void handleFile(String parent, String name) throws IOException {
        this.getWrappedFileFun().handleFile(parent, name);
    }

    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File, java.lang.String)
     */
    public void handleFile(File parent, String name) throws IOException {
        this.getWrappedFileFun().handleFile(parent, name);
    }

    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File)
     */
    public void handleFile(File file) throws IOException {
        this.getWrappedFileFun().handleFile(file);
    }

    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.lang.String)
     */
    public void handleFile(String path) throws IOException {
        this.getWrappedFileFun().handleFile(path);
    }

	public FileFun getWrappedFileFun() {
		return wrappedFileFun;
	}

	public void setWrappedFileFun(FileFun wrappedFileFun) {
		this.wrappedFileFun = wrappedFileFun;
	}

	@Override
	public void close() throws IOException {
		this.wrappedFileFun.close();
	}
	
	

}
