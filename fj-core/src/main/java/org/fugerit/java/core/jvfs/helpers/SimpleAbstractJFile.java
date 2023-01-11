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
 * @(#)SimpleAbstractJFile.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.helpers
 * @creation	: 28-dic-2004 13.58.13
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.fugerit.java.core.jvfs.JVFS;

/*
 * <p></p>
 * 
 * @author  Matteo Franci a.k.a. TUX2
 */
public abstract class SimpleAbstractJFile extends AbstractJFile {

    private long lastModified;
    
    private boolean canRead;
    
    private boolean readOnly;
    
    private boolean canWrite;
    
    /*
     * <p>Crea un nuovo SimpleAbstractJFile</p>
     * 
     * @param path
     * @param jvfs
     */
    public SimpleAbstractJFile(String path, JVFS jvfs) {
        this(path, jvfs, System.currentTimeMillis(), true, true);
    }
    
    public SimpleAbstractJFile(String path, JVFS jvfs, long lastModified, boolean canRead, boolean canWrite) {
        super(path, jvfs);
        this.lastModified = lastModified;
        this.canRead = canRead;
        this.canWrite = canWrite;
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
     * @see org.morozko.java.core.jvfs.JFile#getInputStream()
     */
    public abstract InputStream getInputStream() throws IOException;

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getName()
     */
    public abstract String getName();

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getOutputStream()
     */
    public abstract OutputStream getOutputStream() throws IOException;

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#isFile()
     */
    public abstract boolean isFile() throws IOException;

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#list()
     */
    public abstract String[] list() throws IOException;

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
     * @see org.morozko.java.core.jvfs.JFile#canRead()
     */
    public boolean canRead() throws IOException {
        return this.canRead;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#canWrite()
     */
    public boolean canWrite() throws IOException {
        return this.canWrite && !this.readOnly;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#getLastModified()
     */
    public long getLastModified() throws IOException {
        return this.lastModified;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#setLastModified(long)
     */
    public void setLastModified(long time) throws IOException {
        this.lastModified = time;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFile#setReadOnly()
     */
    public void setReadOnly() throws IOException {
        this.readOnly = true;
    }

}
