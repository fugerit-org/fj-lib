/****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.core 

	Copyright (c) 2006 Morozko

	All rights reserved. This program and the accompanying materials
	are made available under the terms of the Apache License v2.0
	which accompanies this distribution, and is available at
	http://www.apache.org/licenses/
	(txt version : http://www.apache.org/licenses/LICENSE-2.0.txt
	html version : http://www.apache.org/licenses/LICENSE-2.0.html)

   This product includes software developed at
   The Apache Software Foundation (http://www.apache.org/).
</copyright>
*****************************************************************/
/*
 * @(#)AbstractJFile.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.helpers
 * @creation	: 9-dic-2004 14.29.03
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.util.path.PathResolver;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Implementazione astratta dell' interfaccia <code>JFile</code>.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Abstract implemententatin of <code>JFile</code> interface.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public abstract class AbstractJFile implements JFile {

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    public String toString() {
        return this.getClass().getName()+"[path:"+this.path+"]";
    }
    
    /*
     * <jdl:section>
     * 		<jdl:text lang='it'><p>Crea una nuova istanza di AbstractJFile.</p></jdl:text>
     * 		<jdl:text lang='en'><p>Creates a new instance of AbstractJFile.</p></jdl:text>
     * </jdl:section>
     *
     * @param path		<jdl:section>
     * 						<jdl:text lang='it'><p>Il percorso del file relativo al mount.</p></jdl:text>
     * 						<jdl:text lang='en'><p>The mount relative path to the file.</p></jdl:text>
     * 					</jdl:section>
     * @param jvfs		<jdl:section>
     * 						<jdl:text lang='it'><p>Il jvfs di appartenenza del file.</p></jdl:text>
     * 						<jdl:text lang='en'><p>The jvfs of the file.</p></jdl:text>
     * 					</jdl:section>
     */
    public AbstractJFile(String path, JVFS jvfs) {
        this.path = path;
        this.jvfs = jvfs;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#create()
     */
    public abstract boolean create() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#delete()
     */
    public abstract boolean delete() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getChild(java.lang.String)
     */
    public JFile getChild(String name) throws IOException {
        return this.jvfs.getJFile(this.getPathResolver().getChild(this.path, name));
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getInputStream()
     */
    public abstract InputStream getInputStream() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getJVFS()
     */
    public JVFS getJVFS() {
        return this.jvfs;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getName()
     */
    public abstract String getName();
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getOutputStream()
     */
    public abstract OutputStream getOutputStream() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getPath()
     */
    public String getPath() {
        return this.path;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#isDirectory()
     */
    public boolean isDirectory() throws IOException {
        return !this.isFile();
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#isFile()
     */
    public abstract  boolean isFile() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#list()
     */
    public abstract String[] list() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#listFiles()
     */
    public JFile[] listFiles() throws IOException {
        String[] list = this.list();
        JFile[] result = new JFile[list.length];
        for (int k=0; k<list.length; k++) {
            result[k] = this.getChild(list[k]);
        }
        return result;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#mkdir()
     */
    public abstract boolean mkdir() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#mkdirs()
     */
    public abstract boolean mkdirs() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#exists()
     */
    public abstract boolean exists() throws IOException;
    
    
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getRoot()
     */
    public String getRootPath() {
        return this.getPathResolver().getRoot();
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getSeparator()
     */
    public String getPathSeparator() {
        return this.getPathResolver().getSepartor();
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getPathResolver()
     */
    public PathResolver getPathResolver() {
        return this.getJVFS().getPathResolver();
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getReader()
     */
    public Reader getReader() throws IOException {
        return (new InputStreamReader(this.getInputStream()));
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getWriter()
     */
    public Writer getWriter() throws IOException {
        return (new OutputStreamWriter(this.getOutputStream()));
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getFeature(java.lang.String)
     */
    public boolean getFeature(String featureName) {
        return false;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getProperty(java.lang.String)
     */
    public String getProperty(String propertyName) {
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#canRead()
     */
    public abstract boolean canRead() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#canWrite()
     */
    public abstract boolean canWrite() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getLastModified()
     */
    public abstract long getLastModified() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#setLastModified(long)
     */
    public abstract void setLastModified(long time) throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#setReadOnly()
     */
    public abstract void setReadOnly() throws IOException;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#supposedSize()
     */
    public int supposedSize() throws IOException {
        InputStream is = this.getInputStream();
        int size = is.available();
        is.close();
        return size;
    }
    
    private String path;		// the file relative path
    
    private JVFS jvfs;			// the jvfs of the file
    
}
