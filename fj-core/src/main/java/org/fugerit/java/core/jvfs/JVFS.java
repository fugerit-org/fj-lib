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
 * @(#)JVFS.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs
 * @creation	: 17-gen-2005 9.42.25
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs;

import org.fugerit.java.core.util.path.PathResolver;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Interfaccia per la gestione di un file system virtuale.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Interface handling a virtual file system.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public interface JVFS {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce il risolutore di percorsi associato al file system virtuale.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the path resolver of the virtual file system.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>il risolutore di percorsi associato al file system virtuale.</jdl:text>
	 * 				<jdl:text lang='en'>the path resolver of the virtual file system.</jdl:text>  
	 *			</jdl:section>
	 */
    public PathResolver getPathResolver();
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Restituisce il file associato ad un percorso astratto.</jdl:text>
     * 		<jdl:text lang='en'>Returns the file denoted by the abstract path name.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param absPath	<jdl:section>
     * 						<jdl:text lang='it'>Il persorso astratto.</jdl:text>
     * 						<jdl:text lang='en'>The abstract path name.</jdl:text>  
     *					</jdl:section>
     * @return			<jdl:section>
     * 						<jdl:text lang='it'>Il file.</jdl:text>
     * 						<jdl:text lang='en'>The file.</jdl:text>  
     *					</jdl:section>
     */
    public JFile getJFile(String absPath);
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Restituisce la radice del file system virtuale.</jdl:text>
     * 		<jdl:text lang='en'>Returns the root of the virtual file system.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @return	<jdl:section>
     * 				<jdl:text lang='it'>la radice del file system virtuale.</jdl:text>
     * 				<jdl:text lang='en'>the root of the virtual file system.</jdl:text>  
     *			</jdl:section>
     */
    public JFile getRoot();
    
}
