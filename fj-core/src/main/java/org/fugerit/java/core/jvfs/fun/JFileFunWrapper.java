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
 * @(#)JFileFunWrapper.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.fun
 * @creation	: 17-gen-2005 0.12.19
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.fun;

import java.io.IOException;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JFileFun;

/*
 * <p></p>
 * 
 * @author  Matteo Franci aka TUX2
 */
public class JFileFunWrapper implements JFileFun {

    public JFileFunWrapper(JFileFun fileFun) {
        this.wrappedFileFun = fileFun;
    }
    
    private JFileFun wrappedFileFun;
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JFileFun#handle(org.morozko.java.core.jvfs.JFile)
     */
    public void handle(JFile file) throws IOException {
        this.wrappedFileFun.handle(file);
    }

}
