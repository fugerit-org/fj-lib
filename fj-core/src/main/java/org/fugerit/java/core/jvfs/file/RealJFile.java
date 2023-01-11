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
 * @(#)RealJFile.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.file
 * @creation	: 9-dic-2004 14.36.15
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.helpers.AbstractJFile;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Un JFile basato su un file reale.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		A Jfile based upon a real file.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class RealJFile extends AbstractJFile {
	
	/*
	 * (non-Javadoc)
	 * @see org.morozko.java.core.jvfs.helpers.AbstractJFile#toString()
	 */
    public String toString() {
        return this.getClass().getSimpleName()+"[virtualpath:"+this.getPath()+";realpath:"+this.file.getAbsolutePath()+"]";
    }
    
    private File file;		// the real file this JFile is base on

    /*
     * 
     * <jdl:section>
     * 		<jdl:text lang='it'><p>Crea una nuova istanza di RealJFile.</p></jdl:text>
     * 		<jdl:text lang='en'><p>Creates a new instance of RealJFile.</p></jdl:text>
     * </jdl:section>
     *
     * @param path				<jdl:section>
	 * 								<jdl:text lang='it'><p>Il persorso del file relativamente al suo JMount.</p></jdl:text>
	 * 								<jdl:text lang='en'><p>The path of the file relative to his JMount.</p></jdl:text>
	 * 							</jdl:section>	
     * @param jvfs				<jdl:section>
	 * 								<jdl:text lang='it'><p>Il file system virtuale.</p></jdl:text>
	 * 								<jdl:text lang='en'><p>The virtual file system.</p></jdl:text>
	 * 							</jdl:section>	
     * @param file				<jdl:section>
	 * 								<jdl:text lang='it'><p>Il file reale.</p></jdl:text>
	 * 								<jdl:text lang='en'><p>The real file.</p></jdl:text>
	 * 							</jdl:section>	
     */
    public RealJFile(String path, JVFS jvfs, File file) {
        super(path, jvfs);
        this.file = file;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#create()
     */
    public boolean create() throws IOException {
        return this.file.createNewFile();
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#delete()
     */
    public boolean delete() throws IOException {
        return this.file.delete();
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getInputStream()
     */
    public InputStream getInputStream() throws IOException {
        return (new FileInputStream(this.file));
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getName()
     */
    public String getName() {
        return this.file.getName();
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getOutputStream()
     */
    public OutputStream getOutputStream() throws IOException {
        return (new FileOutputStream(this.file));
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#isFile()
     */
    public boolean isFile() throws IOException {
        return this.file.isFile();
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#list()
     */
    public String[] list() throws IOException {
        String[] list = null;
        if (!this.file.exists()) {
            throw (new IOException("File does not exist '"+this.toString()+"'"));
        } else if (!this.file.isDirectory()) {
            throw (new IOException("File is not a directory '"+this.toString()+"'"));
        } else {
            list = this.file.list();
        }
        return list;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#mkdir()
     */
    public boolean mkdir() throws IOException {
        return this.file.mkdir();
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#mkdirs()
     */
    public boolean mkdirs() throws IOException {
        return this.file.mkdirs();
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#exists()
     */
    public boolean exists() throws IOException {
        return this.file.exists();
    }   
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#canRead()
     */
    public boolean canRead() throws IOException {
        return this.file.canRead();
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#canWrite()
     */
    public boolean canWrite() throws IOException {
        return this.file.canWrite();
    }
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getLastModified()
     */
    public long getLastModified() throws IOException {
        return this.file.lastModified();
    }
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#setLastModified(long)
     */
    public void setLastModified(long time) throws IOException {
        this.file.setLastModified(time);
    }
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#setReadOnly(boolean)
     */
    public void setReadOnly() throws IOException {
        this.file.setReadOnly();
    }
    
}
