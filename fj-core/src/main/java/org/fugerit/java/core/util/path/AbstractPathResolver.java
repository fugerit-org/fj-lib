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
 * @(#)AbstractPathResolver.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.util.path
 * @creation	: 12-dic-2004 15.31.05
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.util.path;

import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.lang.helpers.StringUtils;

/*
 * <p></p>
 * 
 * @author  Matteo Franci a.k.a. TUX2
 */
public abstract class AbstractPathResolver implements PathResolver {
    
    public String toString() {
        return this.getClass().getName()+"[root:"+this.getRoot()+";separator:"+this.getSepartor()+"]";
    }
    
    private String root;
    
    private String separator;
    
    private boolean ignoreCase;
    
    protected AbstractPathResolver(String root, String separator, boolean ignoreCase) {
        super();
        this.root = root;
        this.separator = separator;
        this.ignoreCase = ignoreCase;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#getRoot()
     */
    public String getRoot() {
        return this.root;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#getSepartor()
     */
    public String getSepartor() {
        return this.separator;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#getParent(java.lang.String)
     */
    public String getParent(String path) {
        String parent = null;
        int index = path.lastIndexOf(this.getSepartor());
        if (index > 0) {
            parent = path.substring(0, index);
        }
        return parent;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#getName(java.lang.String)
     */
    public String getName(String path) {
        String name = null;
        int index = path.lastIndexOf(this.getSepartor());
        if (index >= 0) {
            name = path.substring(index+1);
        }
        return name;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#getChild(java.lang.String, java.lang.String)
     */
    public String getChild(String parent, String name) {
    	List<String> parts = new ArrayList<>();
    	if ( StringUtils.isNotEmpty( parent ) ) {
    		parts.add( parent );
    	}
    	if ( StringUtils.isNotEmpty( name ) ) {
    		parts.add( name );
    	}
    	return StringUtils.concat( this.getSepartor() , parts );
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#validatePath(java.lang.String)
     */
    public boolean validatePath(String path) {
        boolean valid = (path!=null);
        if (valid) {
            if (this.isIgnoreCase()) {
                valid = path.toLowerCase().indexOf(this.getRoot().toLowerCase())==1;
            } else {
                valid = path.indexOf(this.getRoot())==1;
            }
        }
        return valid;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#isIgnoreCase()
     */
    public boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#equalPath(java.lang.String, java.lang.String)
     */
    public boolean equalPath(String p1, String p2) {        
        boolean eq = (p1!=null);
        if (eq) {
            if (this.isIgnoreCase()) {
                eq = p1.equalsIgnoreCase(p2);
            } else {
                eq = p1.equals(p2);
            }
        }
        return eq;
    }
    
    /* (non-Javadoc)
     * @see org.morozko.java.core.util.path.PathResolver#isRoot(java.lang.String)
     */
    public boolean isRoot(String root) {
        return this.equalPath(this.root, root);
    }
    
}
