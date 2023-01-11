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
 * @(#)JFileFunResult.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.fun
 * @creation	: 17-gen-2005 0.16.50
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.fun;

import org.fugerit.java.core.jvfs.JFileFun;
import org.fugerit.java.core.lang.helpers.Result;
import org.fugerit.java.core.lang.helpers.ResultExHandler;

/*
 * 
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class JFileFunResult extends JFileFunSafe {

    private Result result;
    
    public JFileFunResult(JFileFun fileFun) {
        this(fileFun, new Result());
    }
    
    public JFileFunResult(JFileFun fileFun, Result result) {
        super(fileFun, new ResultExHandler(result));
        this.result = result;
    }

    /*
     * <p>Restituisce il valore di result.</p>
     * 
     * @return il valore di result.
     */
    public Result getResult() {
        return result;
    }
    
    public void reset() {
        this.result.clear();
    }
    
}
