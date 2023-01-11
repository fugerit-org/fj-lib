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
 * @(#)PathException.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.util.path
 * @creation	: 12-dic-2004 15.27.27
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.util.path;

import org.fugerit.java.core.cfg.ConfigException;

/*
 * <p></p>
 * 
 * @author  Matteo Franci a.k.a. TUX2
 */
public class PathException extends ConfigException {

	private static final long serialVersionUID = (long)1000752312341414241L;
	
    /*
     * <p>Crea un nuovo PathException</p>
     * 
     * 
     */
    public PathException() {
        super();
    }

    /*
     * <p>Crea un nuovo PathException</p>
     * 
     * @param message
     */
    public PathException(String message) {
        super(message);
    }

    /*
     * <p>Crea un nuovo PathException</p>
     * 
     * @param message
     * @param cause
     */
    public PathException(String message, Throwable cause) {
        super(message, cause);
    }

    /*
     * <p>Crea un nuovo PathException</p>
     * 
     * @param cause
     */
    public PathException(Throwable cause) {
        super(cause);
    }

}
