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
 * @(#)SystemPathResolver.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.util.path
 * @creation	: 12-dic-2004 15.46.10
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.util.path;

import java.io.File;

/*
 * <p></p>
 * 
 * @author  Matteo Franci a.k.a. TUX2
 */
public class SystemPathResolver extends AbstractPathResolver {

    private final static String WINDOWS = "Windows";
    
    public SystemPathResolver() {
        this(File.listRoots()[0].getAbsolutePath());
    }
    
    public SystemPathResolver(String root) {
        super(root, File.separator, WINDOWS.equalsIgnoreCase(System.getProperty("os.name")));
    }

}
