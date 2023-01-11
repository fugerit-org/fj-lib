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
 * @(#)JFileFun.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs
 * @creation	: 16-gen-2005 15.33.52
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs;

import java.io.IOException;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Funzione che esegue delle manipolzaione a partire da un file.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Function for manipulating files.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public interface JFileFun {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Esegue delle operazioni a partire da un file.</jdl:text>
	 * 		<jdl:text lang='en'>Does operations starting from a file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param file			<jdl:section>
	 * 							<jdl:text lang='it'>Il file in questione.</jdl:text>
	 * 							<jdl:text lang='en'>The file.</jdl:text>  
	 *						</jdl:section>
	 * @throws IOException	<jdl:section>
	 * 							<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 							<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *						</jdl:section>
	 */    
    public void handle(JFile file) throws IOException;
    
}
