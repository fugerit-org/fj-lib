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
 * @(#)JFileFunSafe.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.fun
 * @creation	: 17-gen-2005 0.13.57
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.fun;

import java.io.IOException;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JFileFun;
import org.fugerit.java.core.lang.helpers.ExHandler;

/*
 * <p></p>
 * 
 * @author  Matteo Franci aka TUX2
 */
public class JFileFunSafe extends JFileFunWrapper {

    private ExHandler handler;
    
    /*
     * <p>Crea un nuovo JFileFunSafe</p>
     * 
     * @param fileFun
     */
    public JFileFunSafe(JFileFun fileFun, ExHandler handler) {
        super(fileFun);
        this.handler = handler;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFileFun#handle(org.morozko.java.core.jvfs.JFile)
     */
    public void handle(JFile file) throws IOException {
        try {
            super.handle(file);
        } catch (IOException ioe) {
            this.handler.error(ioe);
        }
    }
    
}
