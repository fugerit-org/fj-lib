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
 * @(#)ZipJFileFun.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.fun
 * @creation	: 16-gen-2005 15.36.26
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.fun;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JFileFun;

/*
 * <p></p>
 * 
 * @author  Matteo Franci aka TUX2
 */
public class ZipJFileFun implements JFileFun {

    private String base;
    
    private ZipOutputStream stream;
    
    public ZipJFileFun(ZipOutputStream stream, String base) {
        this.stream = stream;
        this.base = base;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFileFun#handle(org.morozko.java.core.jvfs.JFile)
     */
    public void handle(JFile file) throws IOException {
        String path = file.getPath();
        String entryName = path.substring(path.indexOf(this.base));
        if (file.isDirectory()) {
            entryName+= file.getPathResolver().getSepartor();
        }
        ZipEntry entry = new ZipEntry(entryName);
        this.stream.putNextEntry(entry);
        StreamIO.pipeStream(file.getInputStream(), this.stream, StreamIO.MODE_CLOSE_IN_ONLY);
        this.stream.closeEntry();
    }
    
}
