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
 * @(#)JMount.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs
 * @creation	: 9-dic-2004 14.48.37
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs;

import org.fugerit.java.core.cfg.ConfigurableObject;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Interfaccia per la gestione di un punto di mount in un 
 * 		file system virtuale.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Interface handling a mount point in a virtual
 * 		file system.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public interface JMount extends ConfigurableObject {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Risolve un file gestito da questo punto di mount.</jdl:text>
	 * 		<jdl:text lang='en'>Resolve a file handled by this mount point.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param jvfs		<jdl:section>
	 * 						<jdl:text lang='it'>Il file system virtuale di cui fa parte il punto di mount.</jdl:text>
	 * 						<jdl:text lang='en'>The jvfs this mount point belongs to.</jdl:text>  
	 *					</jdl:section>		
	 * @param point		<jdl:section>
	 * 						<jdl:text lang='it'>Il punto di mount.</jdl:text>
	 * 						<jdl:text lang='en'>The mount point.</jdl:text>  
	 *					</jdl:section>		
	 * @param relPath	<jdl:section>
	 * 						<jdl:text lang='it'>Il percorso relativo del file.</jdl:text>
	 * 						<jdl:text lang='en'>The relative path of the file.</jdl:text>  
	 *					</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */
    public JFile getJFile(JVFS jvfs, String point, String relPath);
    
}

