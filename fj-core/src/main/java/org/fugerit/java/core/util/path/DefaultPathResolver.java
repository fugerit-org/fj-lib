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
 * @(#)DefaultPathResolver.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.util.path
 * @creation	: 12-dic-2004 15.31.05
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.util.path;

/*
 * <p></p>
 * 
 * @author  Matteo Franci a.k.a. TUX2
 */
public class DefaultPathResolver extends AbstractPathResolver {

	public static final PathResolver DEFAULT = getSecureInstance();
	
    public static final boolean DEF_IC = false;
    
    public static final String DEF_ROOT = "/";

    public static final String DEF_SEPARATOR = "/";

    public static final char DEF_WIN_UNIT = 'J';
    
    public static final char WIN_SEPARATOR = '\\';
    
    public static final PathResolver DEFAULT_RESOLVER = new DefaultPathResolver(DEF_ROOT, DEF_SEPARATOR, DEF_IC);

    public static PathResolver getWindowsPathResolver() throws PathException {
        return getWindowsPathResolver(DEF_WIN_UNIT);
    }
    
    public static PathResolver getWindowsPathResolver(char unit) throws PathException {
        return getInstance(""+unit+":"+WIN_SEPARATOR, "\\", true);
    }    
    
    public static PathResolver getUnixPathResolver() throws PathException {
        return getInstance();
    }
    
    public static PathResolver getSecureInstance() {
    	PathResolver resolver = null;
        try {
			resolver = getInstance();
		} catch (PathException e) {
			e.printStackTrace();
		}
		return resolver;
    }    
    
    public static PathResolver getInstance() throws PathException {
        return getInstance(DEF_ROOT, DEF_SEPARATOR, DEF_IC);
    }
    
    public static PathResolver getInstance(String root, String separator, boolean ignoreCase) throws PathException {
        PathResolver resolver = null;
        if (root==null) {
            throw (new PathException("No root path defined"));
        } else if (separator==null) {
            throw (new PathException("No separator defined"));
        }  else {
            resolver = new DefaultPathResolver(root, separator, ignoreCase);
        }
        return resolver;
    }
        
    private DefaultPathResolver(String root, String separator, boolean ignoreCase) {
        super(root, separator, ignoreCase);
    }

    
}
